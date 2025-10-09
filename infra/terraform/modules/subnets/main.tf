resource "aws_subnet" "this" {
  for_each = { for s in var.subnets : s.name => s }

  vpc_id                  = var.vpc_id
  cidr_block              = each.value.cidr_block
  availability_zone       = each.value.availability_zone
  map_public_ip_on_launch = each.value.map_public_ip

  tags = merge(
    {
      Name                                                       = "${var.env}-${each.value.name}"
      "kubernetes.io/cluster/${var.env}-${var.eks_cluster_name}" = "owned"
      "kubernetes.io/role/${each.value.kubernetes_role}"         = "1"
    },
    {}
  )
}
