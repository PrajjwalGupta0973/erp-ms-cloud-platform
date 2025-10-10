variable "env" {
  description = "Environment name (e.g., dev, staging, prod)"
  type        = string
}

variable "eks_cluster_name" {
  description = "EKS cluster name"
  type        = string
}

variable "eks_version" {
  description = "EKS version"
  type        = string
}

variable "private_subnet_ids" {
  description = "Private subnet IDs for EKS node groups"
  type        = list(string)
}

variable "capacity_type" {
  description = "Type of capacity (ON_DEMAND or SPOT)"
  type        = string
  default     = "ON_DEMAND"
}

variable "instance_types" {
  description = "List of EC2 instance types for node groups"
  type        = list(string)
  default     = ["t3.medium"]
}

variable "desired_size" {
  description = "Desired number of nodes"
  type        = number
  default     = 3
}

variable "max_size" {
  description = "Maximum number of nodes"
  type        = number
  default     = 3
}

variable "min_size" {
  description = "Minimum number of nodes"
  type        = number
  default     = 1
}

variable "node_group_name" {
  description = "EKS node group name"
  type        = string
  default     = "general"
}

variable "labels" {
  description = "Labels to apply to the node group"
  type        = map(string)
  default = {
    role = "general"
  }
}
