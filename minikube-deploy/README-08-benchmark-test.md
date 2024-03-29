# Synthetic Tests
 - Source https://github.com/temporalio/benchmark-workers
 - https://github.com/temporalio/benchmark-matrix/tree/rh-series-2/k8s/monitoring/dashboards


## (OPTION 1) Run with Deployment : Kubectl
```shell
cd soak-test-kbe
kubectl apply -f deploy-kbe.yaml 
kubectl port-forward --address 0.0.0.0 svc/svc-benchmark-workers 28081:8081 -n default

# access via http://192.168.1.205:28081/metrics

kubectl delete -f deploy-kbe.yaml 
```

### Env Details verify.
```
export TEMPORAL_CLI_ADDRESS=localhost:15233
export TEMPORAL_CLI_NAMESPACE=default
./tctl admin cluster describe
shards 512 
./tctl taskqueue list-partition --taskqueue default
10
./tctl workflow count --query 'ExecutionStatus="Running"'
./tctl workflow count --query 'ExecutionStatus="Completed"'
./tctl workflow count --query 'ExecutionStatus="Running" or ExecutionStatus="Completed"'

------- Postgres 
worker - 5 replicas. Transtion / sec : 840, CPU 7 , 
------- Cassandra
worker - 5 replicas. 
Total CPU 6.41 Code 
cassandra 1 instance, using 3.36 CPU  
history 1 instance , using 1.53 CPU 


```
























## (OPTION 2) Run Manually : Start Workers Instance.


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

