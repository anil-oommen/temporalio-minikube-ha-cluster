# UNDER DEVELOPMENT 
## Custom Elastic Search and Kibana for Multi Node Minikube.

- reference [guide](https://www.gooksu.com/2021/05/helm-charts-to-install-the-elastic-stack-using-minikube/)

### setup the repo
```shell
helm repo add elastic-repo https://helm.elastic.co
helm search repo elastic-repo

# show values
helm show values elastic-repo/elasticsearch
```

### Option 1 : install Elastic Search
```shell
helm install es elastic-repo/elasticsearch --version 7.17.3 -f values-elasticsearch-on-multinode.yaml --atomic --timeout 4m 
helm uninstall es
```
