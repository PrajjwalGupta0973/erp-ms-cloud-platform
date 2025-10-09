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
