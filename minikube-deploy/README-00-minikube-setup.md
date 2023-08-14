
## Minikube Startup
### Minikube Single Node (currently not setup to use with PVs.)
```shell
minikube start --memory 8192 --cpus 4

# Dashboard and access from remote 
kubectl proxy --port 14081 --address='0.0.0.0' --disable-filter=true&
minikube dashboard --url --port 14080&

kubectl get nodes
minikube delete
```

### Minikube Multiple Nodes

#### Cluster 1 : UK
```shell
minikube profile list
kubectl get nodes --show-labels

## Cluster Creation and Labeling.

mkdir $HOME/Development/temporal.io/_kb8ukStorage/

## Default Working
minikube start --nodes 1 --memory 12g --cpus 6 --mount --mount-string="$HOME/Development/temporal.io/_kb8ukStorage/:/mnt/host/" -p kb8uk

## Static Decide on CPU and Memory for Nodes. MAX
minikube start --nodes 2 --memory max --cpus max --mount --mount-string="$HOME/Development/temporal.io/_kb8ukStorage/:/mnt/host/" -p kb8uk


minikube start --nodes 2 --memory 6g --cpus 3 --mount --mount-string="$HOME/Development/temporal.io/_kb8ukStorage/:/mnt/host/" -p kb8uk

## Dynamic Resource Allocation. Values for Second too low.
minikube start --nodes 2 --mount --mount-string="$HOME/Development/temporal.io/_kb8ukStorage/:/mnt/host/" -p kb8uk

kubectl describe nodes

kubectl label nodes kb8uk kb8uk-m02 kubernetes.io/region=uk
kubectl label nodes kb8uk kubernetes.io/rack=one
kubectl label nodes kb8uk-m02 kubernetes.io/rack=one



# Dashboard and access from remote 
kubectl proxy --port 14081 --address='0.0.0.0' --disable-filter=true&
minikube dashboard --url --profile kb8uk &


# cleanup 
minikube stop -p kb8uk
minikube delete -p kb8uk

```
### Persistance Volume & Other Resources Setup.
```shell

kubectl apply -f ./minikube-setup-resources.yaml 
kubectl delete -f ./minikube-setup-resources.yaml  
```

### Add Additional Storage Class and Provider
 required for cassandra
```shell
minikube addons enable default-storageclass -p kb8uk
minikube addons enable storage-provisioner -p kb8uk
```

### Access Clusters
 - [dashboard](http://192.168.1.205:14081/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/)
```shell
kubectl get nodes --show-labels
minikube status -p mknode
```
### Enable Metrics
```
 minikube addons list | grep metrics
 minikube addons enable metrics-server -p kb8uk
```

#### Cluster 2 : AP (Under Development)
 - Only One Profile can be accessed via dashboard on a single Machine!
 - kubectl need maintain multiple configs (kubectl config view)
 - Dont use secondy cluster yet!


## Utility 
```
docker pull google/cloud-sdk
kubectl run --namespace default shellbox --rm --tty -i --restart='Never' \
   --image google/cloud-sdk 
```

```
 # Check Elastic Search Indexes available 
 curl http://es-elasticsearch-master:9200
 curl --user : http://es-elasticsearch-master:9200/temporal_visibility_v1_dev
```