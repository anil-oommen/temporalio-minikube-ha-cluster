
## Custom Prometheus for Multi Node Minikube.

- reference [promethus-values](https://github.com/prometheus-community/helm-charts/blob/main/charts/prometheus/values.yaml)

### setup the repo
```shell
helm repo add prometheus-repo https://prometheus-community.github.io/helm-charts
helm search repo prometheus-repo
```

### Option 1 : install prometheus basic
```shell
helm install pr prometheus-repo/prometheus -f values-prometheus-on-multinode.yaml --atomic --timeout 180s 
helm uninstall pr
```

### Option 1 : install prometheus operator
```shell
TBD
```

