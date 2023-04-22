

helm dependencies update

/* PREQUISTE POSTGRES RUNNING 
    https://github.com/bitnami/charts/tree/main/bitnami/postgresql/#installing-the-chart
helm repo add bitnami-repo https://charts.bitnami.com/bitnami 

helm install pg bitnami-repo/postgresql  
helm upgrade pg bitnami-repo/postgresql  
helm uninstall pg

#Check Password in the secret from Kubrenetes , can be one setup first.
# added postgres-setup-job.yaml to create the required daabase.

*/

cd r1191-helm-charts
helm install -f values/values.postgresql.yaml defa \
  --set elasticsearch.enabled=false \
  --set server.config.persistence.default.sql.user=postgres \
  --set server.config.persistence.default.sql.password=it50fg3lFY \
  --set server.config.persistence.visibility.sql.user=postgres \
  --set server.config.persistence.visibility.sql.password=it50fg3lFY \
  --set server.config.persistence.default.sql.host=pg-postgresql \
  --set server.config.persistence.visibility.sql.host=pg-postgresql \
  --set server.config.persistence.default.sql.database=temporal \
  --set server.config.persistence.visibility.sql.database=temporal_visibility \
  --atomic . --timeout 120s
