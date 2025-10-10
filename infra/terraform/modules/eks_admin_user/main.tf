data "aws_caller_identity" "current" {}

# IAM Role for EKS Admin
resource "aws_iam_role" "eks_admin" {
  name = "${var.eks_cluster_name}-eks-admin"

  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
POLICY
}

# Attach Admin Policy to Role
resource "aws_iam_policy" "eks_admin_policy" {
  name   = "${var.eks_cluster_name}-EKSAdminPolicy"
  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["eks:*"],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": "iam:PassRole",
      "Resource": "*",
      "Condition": {
        "StringEquals": {"iam:PassedToService": "eks.amazonaws.com"}
      }
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "eks_admin_attach" {
  role       = aws_iam_role.eks_admin.name
  policy_arn = aws_iam_policy.eks_admin_policy.arn
}

resource "aws_iam_user" "manager" {
  name = var.eks_admin_user_name
}

resource "aws_iam_policy" "eks_assume_admin" {
  name   = "${var.eks_cluster_name}-AssumeAdminPolicy"
  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["sts:AssumeRole"],
      "Resource": "${aws_iam_role.eks_admin.arn}"
    }
  ]
}
POLICY
}

resource "aws_iam_user_policy_attachment" "manager_attach" {
  user       = aws_iam_user.manager.name
  policy_arn = aws_iam_policy.eks_assume_admin.arn
}

resource "aws_eks_access_entry" "manager" {
  cluster_name      = var.eks_cluster_name
  principal_arn     = aws_iam_role.eks_admin.arn
  kubernetes_groups = [var.kubernetes_admin_group_name]
}
