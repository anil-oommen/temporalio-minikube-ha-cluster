
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

#### Cluster 1 : UK
```shell
minikube profile list
kubectl get nodes --show-labels

## Cluster Creation and Labeling.

mkdir $HOME/Development/temporal.io/_kb8ukStorage/

minikube start --nodes 2 --mount --mount-string="$HOME/Development/temporal.io/_kb8ukStorage/:/mnt/host/" -p kb8uk

kubectl label nodes kb8uk kubernetes.io/region=uk
kubectl label nodes kb8uk-m02 kubernetes.io/region=uk

# Dashboard and access from remote 
kubectl proxy --port 14081 --address='0.0.0.0' --disable-filter=true&
minikube dashboard --url --profile kb8uk &


# cleanup 
minikube delete -p kb8uk

```

### Access Clusters
 - [dashboard](http://192.168.1.205:14081/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/)
```shell
kubectl get nodes --show-labels
minikube status -p mknode
```

### Persistance Volume Setup.
```shell

kubectl apply -f ./minikube-setup-resources.yaml 
kubectl delete -f ./minikube-setup-resources.yaml  
```

#### Cluster 2 : AP (Under Development)
 - Only One Profile can be accessed via dashboard on a single Machine!
 - kubectl need maintain multiple configs (kubectl config view)
 - Dont use secondy cluster yet!
