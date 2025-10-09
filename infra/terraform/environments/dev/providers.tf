provider "aws" {
  region  = var.region
  profile = "terraform-vpc-admin-role-profile" # Remove this later and provide AWS CLI profile access keys in default section in aws credential file 
}

terraform {
  required_version = ">= 1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.49"
    }
  }
}
