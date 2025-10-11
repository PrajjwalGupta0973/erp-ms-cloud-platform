variable "eks_cluster_name" {

}

variable "vpc_id" {
}

variable "namespace" {

  default = "kube-system"
}

variable "helm_chart_version" {

  default = "1.7.2"
}

variable "iam_policy_file_path" {

}
