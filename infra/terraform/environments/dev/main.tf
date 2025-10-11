module "vpc_and_network" {
  source = "../../modules/vpc_and_network"
  env    = var.env
  vpc    = var.vpc
}
module "subnets" {
  source           = "../../modules/subnets"
  vpc_id           = module.vpc_and_network.vpc_id
  env              = var.env
  eks_cluster_name = var.eks_cluster_name
  subnets          = var.subnets
}
module "internet_gateway" {
  source = "../../modules/internet_gateway"
  vpc_id = module.vpc_and_network.vpc_id
  env    = var.env
}

module "nat_gateway" {
  source           = "../../modules/nat_gateway"
  env              = var.env
  public_subnet_id = module.subnets.public_subnet_ids[0]
  depends_on       = [module.internet_gateway]
}
module "route_tables" {
  source              = "../../modules/route"
  env                 = var.env
  vpc_id              = module.vpc_and_network.vpc_id
  public_subnet_ids   = module.subnets.public_subnet_ids
  private_subnet_ids  = module.subnets.private_subnet_ids
  nat_gateway_id      = module.nat_gateway.id
  internet_gateway_id = module.internet_gateway.id
}

module "eks_cluster" {
  source = "../../modules/eks"

  env                     = var.env
  eks_cluster_name        = var.eks_cluster_name
  eks_version             = "1.29"
  private_subnet_ids      = module.subnets.private_subnet_ids
  endpoint_private_access = false
  endpoint_public_access  = true
  region                  = var.region
}
module "eks_nodes" {
  source             = "../../modules/eks_nodes"
  env                = var.env
  eks_cluster_name   = module.eks_cluster.eks_cluster_name
  eks_version        = module.eks_cluster.eks_cluster_version
  private_subnet_ids = module.subnets.private_subnet_ids

  capacity_type  = "ON_DEMAND"
  instance_types = ["t3.medium"]
  desired_size   = 3
  max_size       = 3
  min_size       = 1
}
module "eks_admin_user" {
  source                      = "../../modules/eks_admin_user"
  env                         = var.env
  eks_cluster_name            = module.eks_cluster.eks_cluster_name
  eks_admin_user_name         = var.eks_admin_username
  kubernetes_admin_group_name = var.kubernetes_admin_group_name
}

# data "aws_eks_cluster" "eks" {
#   name       = module.eks_cluster.eks_cluster_name
#   depends_on = [module.eks_nodes]
# }

# data "aws_eks_cluster_auth" "eks" {
#   name       = module.eks_cluster.eks_cluster_name
#   depends_on = [module.eks_nodes]
# }

provider "kubernetes" {
  host                   = module.eks_cluster.eks_cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks_cluster.eks_cluster_ca_certificate)
  exec {
    api_version = "client.authentication.k8s.io/v1beta1"
    command     = "aws"
    args        = ["eks", "get-token", "--cluster-name", module.eks_cluster.eks_cluster_id]
  }

}

provider "helm" {

  kubernetes = {
    host                   = module.eks_cluster.eks_cluster_endpoint
    cluster_ca_certificate = base64decode(module.eks_cluster.eks_cluster_ca_certificate)
    exec = {
      api_version = "client.authentication.k8s.io/v1beta1"
      command     = "aws"
      args        = ["eks", "get-token", "--cluster-name", module.eks_cluster.eks_cluster_id]
    }

  }
}

module "metrics_server" {
  source = "../../modules/metrics_server"

  metrics_server_values_yaml_file_path = var.metrics_server_values_yaml_file_path
  dependence_on = [
    module.eks_nodes.node_group
  ]
}
module "pod_identity_addon" {
  source       = "../../modules/eks_addon"
  cluster_name = module.eks_cluster.eks_cluster_name

  depends_on = [
    module.eks_cluster
  ]
}
module "cluster_autoscaler" {

  source               = "../../modules/cluster_autoscaler"
  eks_cluster_name     = module.eks_cluster.eks_cluster_name
  region               = var.region
  service_account_name = var.auto_scaler_service_account_name
  namespace            = var.cluster_auto_scaler_namespace
  depends_on           = [module.metrics_server]
}

module "aws_lbc" {
  source = "../../modules/aws_lbc"

  eks_cluster_name = module.eks_cluster.eks_cluster_name
  vpc_id           = module.vpc_and_network.vpc_id

  iam_policy_file_path = var.awc_lbc_controller_role_policy_file_path
}
module "nginx_controller" {
  source           = "../../modules/nginx_controller"
  values_file_path = var.nginx_values_yaml_file_path

  depends_on = [
    module.aws_lbc.helm_release_name,
    module.eks_cluster.wait_for_eks
  ]
}


