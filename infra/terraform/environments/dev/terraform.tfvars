region = "us-east-1"
env = "dev"
vpc = {
  cidr_block           = "10.0.0.0/20"
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = { }
}
subnets = [
  {
    name              = "private-zone1"
    cidr_block        = "10.0.8.0/22"
    availability_zone = "us-east-1a"
    map_public_ip     = false
    kubernetes_role   = "role/internal-elb"
  },
  {
    name              = "private-zone2"
    cidr_block        = "10.0.12.0/22"
    availability_zone = "us-east-1b"
    map_public_ip     = false
    kubernetes_role   = "role/internal-elb"
  },
  {
    name              = "public-zone1"
    cidr_block        = "10.0.0.0/22"
    availability_zone = "us-east-1a"
    map_public_ip     = true
    kubernetes_role   = "role/elb"
  },
  {
    name              = "public-zone2"
    cidr_block        = "10.0.4.0/22"
    availability_zone = "us-east-1b"
    map_public_ip     = true
    kubernetes_role   = "role/elb"
  }
]
eks_cluster_name = "erp"
eks_admin_username = "eks-manager"
kubernetes_admin_group_name = "my-admin"
metrics_server_values_yaml_file_path= "./values/metrics-server.yaml"
cluster_auto_scaler_namespace = "kube-system"
auto_scaler_service_account_name = "cluster-autoscaler"

awc_lbc_controller_role_policy_file_path = "./values/AWSLoadBalancerController.json"
nginx_values_yaml_file_path = "./values/nginx-ingress.yaml"