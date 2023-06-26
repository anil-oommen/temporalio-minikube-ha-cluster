# Synthetic Tests
 - Source https://github.com/temporalio/benchmark-workers
 - https://github.com/temporalio/benchmark-matrix/tree/rh-series-2/k8s/monitoring/dashboards


## Run with Deployment : Kubectl
```shell
cd soak-test-kbe
kubectl apply -f deploy-kbe.yaml 
kubectl port-forward --address 0.0.0.0 svc/svc-benchmark-workers 28081:8081 -n default

# access via http://192.168.1.205:28081/metrics

kubectl delete -f deploy-kbe.yaml 
```



## Run Manually : Start Workers Instance.


```shell
kubectl run benchmark-worker --image ghcr.io/temporalio/benchmark-workers:main \
    --image-pull-policy Always \
    --env "TEMPORAL_GRPC_ENDPOINT=tio1-temporal-frontend:7233" \
    --env "TEMPORAL_NAMESPACE=default" \
    --env "TEMPORAL_TASK_QUEUE=benchmark" \
    --env "TEMPORAL_WORKFLOW_TASK_POLLERS=16" \
    --env "TEMPORAL_WORKFLOW_ACTIVITY_TASK_POLLERS=8" \
    --env "PROMETHEUS_ENDPOINT=0.0.0.0:8081"
```

kubectl exec --stdin --tty benchmark-worker -- /bin/bash

### Run Launcher , 
 - -c Concurrent Workflows 
 - -w wait for workflows to complete (default true)

```shell
kubectl run benchmark-launcher --image ghcr.io/temporalio/benchmark-workers:main \
    --image-pull-policy Always \
    --env "TEMPORAL_GRPC_ENDPOINT=tio1-temporal-frontend:7233" \
    --command -- runner -c 100 -t ExecuteActivity '{ "Count": 3, "Activity": "Echo", "Input": { "Message": "test" } }'
```

