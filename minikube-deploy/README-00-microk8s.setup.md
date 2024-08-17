
## MicroK8s Startup
### MicroK8s Single Node 
```shell

microk8s kubectl get all --all-namespaces
microk8s dashboard-proxy

microk8s kubectl port-forward -n kube-system service/kubernetes-dashboard 10443:443
https://192.168.1.205:10443/#/workloads?namespace=default
```
### Install Resources in Skaffold
```
@see
tio.k.reset
```