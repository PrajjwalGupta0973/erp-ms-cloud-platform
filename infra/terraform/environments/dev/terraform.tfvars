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
eks_cluster_name = "erp-dev-cluster"