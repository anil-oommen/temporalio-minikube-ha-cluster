https://github.com/bitnami/charts/tree/main/bitnami/postgresql/#installing-the-chart
helm repo add bitnami-repo https://charts.bitnami.com/bitnami 


#### Basic when Single Node Minikube
helm install pg bitnami-repo/postgresql --atomic --timeout 180s 

####  if minikube running multi nodes select the first primary node 
helm install pg bitnami-repo/postgresql -f values-postgres-selectnode.yaml --atomic --timeout 180s 
helm upgrade pg bitnami-repo/postgresql  
helm uninstall pg


#Check Password in the secret from Kubrenetes , can be one setup first.
# added postgres-setup-job.yaml to create the required daabase and apply schema.
echo $(kubectl get secret --namespace default pg-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d)
