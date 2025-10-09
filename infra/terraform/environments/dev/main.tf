module "vpc_and_network" {
  source = "../../modules/vpc_and_network"
  env    = var.env
  vpc    = var.vpc
}
