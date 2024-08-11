## Testing Kubernetes Cluster Failure.

### Cordon and Drain
```shell
kubectl get nodes
kubectl cordon mknode
kubectl delete pod pg-postgresql-0


kubectl drain mknode
kubectl drain mknode --grace-period 0
kubectl get nodes
# 
kubectl uncordon NODE
```
