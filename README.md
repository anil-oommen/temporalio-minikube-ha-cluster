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