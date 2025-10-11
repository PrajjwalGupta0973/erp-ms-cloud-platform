variable "region" {
  type = string
}
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

variable "eks_cluster_name" {
  type = string
}

variable "subnets" {
  type = list(object({
    name              = string
    cidr_block        = string
    availability_zone = string
    map_public_ip     = bool
    kubernetes_role   = string
  }))
}

variable "eks_admin_username" {

}
variable "kubernetes_admin_group_name" {

}

variable "metrics_server_values_yaml_file_path" {

}

variable "cluster_auto_scaler_namespace" {

}
variable "auto_scaler_service_account_name" {

}
variable "awc_lbc_controller_role_policy_file_path" {

}
variable "nginx_values_yaml_file_path" {

}
