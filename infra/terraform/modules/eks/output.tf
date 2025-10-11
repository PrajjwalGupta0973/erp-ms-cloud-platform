output "eks_cluster_id" {
  value = aws_eks_cluster.eks.id
}

output "eks_cluster_name" {
  value = aws_eks_cluster.eks.name
}

output "eks_cluster_endpoint" {
  value = aws_eks_cluster.eks.endpoint
}

output "eks_cluster_arn" {
  value = aws_eks_cluster.eks.arn
}
output "eks_cluster_version" {
  value = aws_eks_cluster.eks.version
}

output "cluster_endpoint" {
  value = aws_eks_cluster.eks.endpoint
}

# output "cluster_ca" {
#   value = aws_eks_cluster.eks.certificate_authority[0].data
# }

# output "cluster_token" {
#   value = data.aws_eks_cluster_auth.eks.token
# }
# output "wait_for_eks" {
#   value = null_resource.wait_for_eks.id
# }
# modules/eks_cluster/outputs.tf
output "eks_cluster_ca_certificate" {
  value = aws_eks_cluster.eks.certificate_authority[0].data
}
