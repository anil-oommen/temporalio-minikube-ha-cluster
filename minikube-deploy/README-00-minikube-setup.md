
## Minikube Startup
### Minikube Single Node
```shell
minikube start --memory 8192 --cpus 4

# Dashboard and access from remote 
kubectl proxy --port 14081 --address='0.0.0.0' --disable-filter=true&
minikube dashboard --url --port 14080&

kubectl get nodes
minikube delete
```

### Minikube Multiple Nodes
```shell
minikube start --nodes 3 -p mknode

# Dashboard and access from remote 
kubectl proxy --port 14081 --address='0.0.0.0' --disable-filter=true&
minikube dashboard --url --profile mknode&

kubectl get nodes --profile mknode
minikube delete
```

### Access Clusters
 - [dashboard](http://192.168.1.205:14081/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/)
```shell
kubectl get nodes --show-labels
minikube status -p mknode
```

