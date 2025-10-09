variable "vpc_id" {
  description = "VPC ID to create subnets in"
  type        = string
}

variable "env" {
  description = "Environment name"
  type        = string
}

variable "eks_cluster_name" {
  description = "EKS cluster name (for tagging)"
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
