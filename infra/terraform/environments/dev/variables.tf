variable "region" {
  description = "AWS region for this environment"
  type        = string
}
variable "env" {
  description = "Environment name (e.g., dev, staging, prod)"
  type        = string
}

variable "vpc" {
  description = "VPC configuration"
  type = object({
    cidr_block           = string
    enable_dns_support   = bool
    enable_dns_hostnames = bool
    tags                 = map(string)
  })

}

variable "eks_cluster_name" {
  description = "Name of the EKS cluster"
  type        = string
}

variable "subnets" {
  description = "List of subnet definitions"
  type = list(object({
    name              = string
    cidr_block        = string
    availability_zone = string
    map_public_ip     = bool
    kubernetes_role   = string
  }))
}

