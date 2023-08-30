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


### Debugging Why temporal-schema-setup would fail
 - Mostly can fail with "400, Bad Request "resource_already_exists_exception","reason"
 - current scripts on smart enough.
 - if so use schema.setup.enabled.


```
kubectl run --namespace default tio-admin-client --rm --tty -i --restart='Never' \
   --image temporalio/admin-tools:1.21.5 -- bash

# the complete job 

curl --insecure -X PUT -v --user elastic:123456 https://es-elasticsearch-master:9200/_template/temporal_visibility_v1_template -H "Content-Type: application/json" --data-binary "@schema/elasticsearch/visibility/index_template_v7.json" 2>&1 && curl --insecure -X PUT -v --user elastic:123456 https://es-elasticsearch-master:9200/temporal_visibility_v1_dev 2>&1

# part 1 
curl --insecure -X PUT -v --user elastic:123456 https://es-elasticsearch-master:9200/_template/temporal_visibility_v1_template -H "Content-Type: application/json" --data-binary "@schema/elasticsearch/visibility/index_template_v7.json" 

# part 2
curl --insecure -X PUT -v --user elastic:123456 https://es-elasticsearch-master:9200/temporal_visibility_v1_dev
```










### Diagnostics 
curl -v --insecure --user elastic:123456 https://127.0.0.1:9200/
###


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
