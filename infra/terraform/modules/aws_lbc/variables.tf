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

variable "cluster_autoscaler_helm_release_name" {

  default = ""
}

variable "iam_policy_file_path" {

}
