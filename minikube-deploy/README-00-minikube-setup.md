
minikube start
minikube start --memory 8192 --cpus 4
minikube dashboard --url
kubectl get nodes
minikube delete


## Runnig with Multiple Nodes
minikube start --nodes 3 -p mknode
minikube dashboard --url --profile mknode

##
kubectl proxy --port 14081 --address='0.0.0.0' --disable-filter=true&
minikube dashboard --url --profile mknode --port 14080&
    http://192.168.1.205:14081/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/


kubectl get nodes
kubectl get nodes --show-labels
minikube status -p mknode


