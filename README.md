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

### Observability Notes :

Prometheus Evaluate Metrics :
 [prometheus](http://192.168.1.205:18080/graph?g0.expr=&g0.tab=1&g0.stacked=0&g0.show_exemplars=0&g0.range_input=1h)
 - Get all Metrics Names ```group by(__name__) ({__name__!=""})```

History Service Dashboards > Workflow Task Insight | Workflow Task Breakdown
 - schedule_activity count 
 - Prometheus Query ```schedule_activity_command {operation="RespondWorkflowTaskCompleted"}```
 - , complete_workflow count.

![grafana-temporal-worker.png](grafana-temporal-worker.png "grafana-temporal-worker.png")
