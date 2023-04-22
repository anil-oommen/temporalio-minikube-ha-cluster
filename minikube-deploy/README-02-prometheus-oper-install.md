Custom Promentehus and Grafana Required as Multi Node Not working with default depenenciy


helm install pk prometheus-community/kube-prometheus-stack --atomic --timeout 180s 
helm install pr prometheus-repo/prometheus-operator --atomic --timeout 180s 



helm install pr prometheus-repo/prometheus -f values-prometheus-multinode.yaml --atomic --timeout 180s 
helm uninstall pr



# https://github.com/prometheus-community/helm-charts/blob/main/charts/prometheus/values.yaml

