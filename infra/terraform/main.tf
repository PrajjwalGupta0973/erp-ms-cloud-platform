# main.tf
locals {
  env = "dev"
  region = "us-east-1"
  zone1 = "us-east-1a"
  zone2 = "us-east-1b"
  eks_cluster_name = "erp"
  eks_version = "1.29"
}



