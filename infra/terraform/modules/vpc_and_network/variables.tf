variable "env" {

  type = string
}

variable "vpc" {

  type = object({
    cidr_block           = string
    enable_dns_support   = bool
    enable_dns_hostnames = bool
    tags                 = map(string)
  })
}
