output "release_name" {
  value = helm_release.metrics_server.name
}
output "metrics_server_values_yaml_file_path" {
  value = var.metrics_server_values_yaml_file_path
}
