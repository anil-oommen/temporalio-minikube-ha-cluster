# UNDER DEVELOPMENT 
## Custom Elastic Search and Kibana for Multi Node Minikube.

- reference [guide](https://www.gooksu.com/2021/05/helm-charts-to-install-the-elastic-stack-using-minikube/)
- https://github.com/elastic/helm-charts/tree/main/elasticsearch
### setup the repo
```shell
helm repo add elastic-repo https://helm.elastic.co
helm search repo elastic-repo

# show values
helm show values elastic-repo/elasticsearch
```

## Storage Classes if using Persistent Volume.
 - https://github.com/elastic/helm-charts/tree/main/elasticsearch/examples/minikube
```
minikube addons enable default-storageclass -p kb8uk
minikube addons enable storage-provisioner -p kb8uk
``` 

### Option 1 : install Elastic Search
```shell
helm install es elastic-repo/elasticsearch --version 7.17.3 -f values-elasticsearch-on-multinode.yaml --atomic --timeout 4m 
helm upgrade es elastic-repo/elasticsearch --version 7.17.3 -f values-elasticsearch-on-multinode.yaml --atomic --timeout 4m 
helm uninstall es
```
