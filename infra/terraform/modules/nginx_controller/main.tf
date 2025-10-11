resource "helm_release" "nginx_controller" {
  name             = "external"
  repository       = "https://kubernetes.github.io/ingress-nginx"
  chart            = "ingress-nginx"
  namespace        = var.namespace
  create_namespace = true
  version          = var.helm_chart_version
  values           = [file(var.values_file_path)]
  timeout          = 600
}
