
## Postgres Setup
- Single Instance for all Clusters.
- database & schema setup by temporal charts as needed. 
- Source : [bitnami/postgres](https://github.com/bitnami/charts/tree/main/bitnami/postgresql/#installing-the-chart)

```shell
helm repo add bitnami-repo https://charts.bitnami.com/bitnami 
```

#### Basic when Single Node Minikube
```shell
helm install pg bitnami-repo/postgresql --atomic --timeout 180s 
```

####  if minikube running multi nodes select the first primary node
```shell
helm install pg bitnami-repo/postgresql -f values-postgres-on-multinode.yaml --atomic --timeout 180s 
helm upgrade pg bitnami-repo/postgresql  
helm uninstall pg
```

#### get password from Kubernetes secret 
```shell
echo $(kubectl get secret --namespace default pg-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d)
```

