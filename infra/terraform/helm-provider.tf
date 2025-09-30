data "aws_eks_cluster" "eks_helm" {
  name = aws_eks_cluster.eks.name
}
data "aws_eks_cluster_auth" "eks" {
  name = aws_eks_cluster.eks.name
}

provider "helm" {
  kubernetes = {
    host                   = data.aws_eks_cluster.eks_helm.endpoint
    cluster_ca_certificate = base64decode(data.aws_eks_cluster.eks_helm.certificate_authority[0].data)
    token                  = data.aws_eks_cluster_auth.eks.token
  }
}
