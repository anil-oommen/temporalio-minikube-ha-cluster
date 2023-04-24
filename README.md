## Running Temporal Clusters on Minikube
 Evaluating a Setup for Temporal Kubernetes running on Minikube. 

### Temporal Features & Setup being Evaluated.  
 - Archival Features with FileStore
 - Advanced visibility with ES & CustomSearch Attributes.
 - Monitoring with Grafana+Prometheus  
 - Multi Cluster Replication.

### Modules
 - [minikube-deploy](minikube-deploy/) Helm Charts & Setup Instructions for Components
 - [spring-boot-app](spring-boot-app/README.md) with micrometer & prometheus and Helm Charts for deployment.

### Observability With Grafana :

#### Resources:
 - Recommended Metrics (https://docs.temporal.io/application-development/worker-performance)

#### Prometheus Evaluate Metrics :
 [prometheus](http://192.168.1.205:18080/graph?g0.expr=&g0.tab=1&g0.stacked=0&g0.show_exemplars=0&g0.range_input=1h)
 - Get all Metrics Names ```group by(__name__) ({__name__!=""})```

#### Spring Application Metrics. (Filter by {job="prometheus-spring-pods"})
Grafana Metrics Used, used prometheus to analyse e.g. ```temporal_worker_task_slots_available{job="prometheus-spring-pods"}```
```
	sum by(hostname) (temporal_worker_task_slots_available{job="prometheus-spring-pods"})
	sum by(hostname) (temporal_workflow_task_schedule_to_start_latency_seconds_max{worker_type="WorkflowWorker",job="prometheus-spring-pods"})
	sum by(hostname) (temporal_workflow_task_schedule_to_start_latency_seconds_sum{worker_type="WorkflowWorker",job="prometheus-spring-pods"})
	sum by(activity_type) (temporal_activity_schedule_to_start_latency_seconds_max{job="prometheus-spring-pods"})
	sum by(activity_type) (temporal_activity_schedule_to_start_latency_seconds_sum{job="prometheus-spring-pods"})
	sum by(hostname) (temporal_sticky_cache_size{job="prometheus-spring-pods"})
	sum by(hostname) (temporal_workflow_active_thread_count{job="prometheus-spring-pods"})
```

![grafana-temporal-worker.png](grafana-temporal-worker.png "grafana-temporal-worker.png")

#### Section Under development.

History Service Dashboards > Workflow Task Insight | Workflow Task Breakdown
 - schedule_activity count 
 - Prometheus Query ```schedule_activity_command {operation="RespondWorkflowTaskCompleted"}```
 - , complete_workflow count.
 
 Spring Application Worker, Metrics added.  
 - Seconds ```sum by(hostname)(temporal_workflow_endtoend_latency_seconds_max{job="prometheus-spring-pods"})```
 - Counter ```temporal_workflow_completed_total{job="prometheus-spring-pods"}```
 - ```temporal_workflow_active_thread_count{job="prometheus-spring-pods"}```
 - Guage ```temporal_activity_schedule_to_start_latency_seconds_max{profile="kbe"}```

 