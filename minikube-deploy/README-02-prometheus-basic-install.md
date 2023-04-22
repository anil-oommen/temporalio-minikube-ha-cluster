Custom Promentehus and Grafana Required as Multi Node Not working with default depenenciy

helm repo add prometheus-repo https://prometheus-community.github.io/helm-charts
helm search repo prometheus-repo

helm install pr prometheus-repo/prometheus -f values-prometheus-multinode.yaml --atomic --timeout 180s 
helm uninstall pr

nodeExporter


# https://github.com/prometheus-community/helm-charts/blob/main/charts/prometheus/values.yaml

