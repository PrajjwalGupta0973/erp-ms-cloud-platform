output "node_role_arn" {
  description = "IAM Role ARN for EKS nodes"
  value       = aws_iam_role.nodes.arn
}

output "node_group_name" {
  value = aws_eks_node_group.general.node_group_name
}
