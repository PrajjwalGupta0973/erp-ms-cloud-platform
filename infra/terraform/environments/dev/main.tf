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
  eks_cluster_name        = "erp"
  eks_version             = "1.29"
  private_subnet_ids      = module.subnets.private_subnet_ids
  endpoint_private_access = false
  endpoint_public_access  = true
}
