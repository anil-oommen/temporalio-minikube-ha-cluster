
# init , skaffold installed with gcloud.
```
skaffold config set --global local-cluster true
skaffold init --skip-build

```

## Cert Manager
#### https://cert-manager.io/docs/installation/helm/
#### Sync Certs to other namespaces , use https://cert-manager.io/docs/tutorials/syncing-secrets-across-namespaces/
```
helm repo add jetstack https://charts.jetstack.io
helm search repo jetstack

helm repo add emberstack https://emberstack.github.io/helm-charts
helm search repo reflector
-- see annotations added to secrets to copy to other namespace.

helm repo add elastic-repo https://helm.elastic.co
helm search repo elastic-repo

helm repo add prometheus-repo https://prometheus-community.github.io/helm-charts
helm search repo prometheus-repo

helm repo add grafana https://grafana.github.io/helm-charts
helm search repo grafana

helm repo add incubator https://charts.helm.sh/incubator


```

### Deploy CertManager and Application Cert CRDS.
```
kubectl create namespace cert-manager
skaffold dev --filename='skaffold-certmanager.yaml'

# verify 
kubectl describe certificate cert-selfsigned-general -n default
# secret generated 'cert-selfsigned-general-secret' , with jks and pkcs12

# Dev and Verification Commands
kubectl get certificates --all-namespaces
kubectl describe certificate  general-cert -n cert-manager
# if not ' The certificate has been successfully issued' then still in progress.

 kubectl get Issuers,ClusterIssuers,Certificates,CertificateRequests,Orders,Challenges --all-namespaces

```

## Utility Container

# Simple Shell no Certs
```
kubectl run --namespace default shellbox --rm --tty -i --restart='Never' \
   --image google/cloud-sdk 
```

# Shell With Certs
```
# Commands from Utility, to diagnize the certificate.
# for Containers with Volume Mount.
 kubectl apply -f minikube-utility-shell.yaml
 kubectl exec --stdin --tty shellbox -- /bin/bash

 kubectl exec --stdin --tty tio-temporal-history-f9448d89-6mtsv -- /bin/bash

 cd /etc/config/...

 # https://www.sslshopper.com/article-most-common-openssl-commands.html
 openssl x509 -in tls.crt -text -noout
 # Verify Cert without Trusting CA
 openssl verify tls.crt
 # Verify Cert Providing CA
 
 openssl verify -CAfile ca-keystore/ca.crt keystore/tls.crt
 openssl pkcs12 -info -in truststore.p12 -passin pass:XXXXKEYSTOREPASSWORD
 openssl pkcs12 -info -in truststore.p12 -passin pass:XXXXKEYSTOREPASSWORD
 openssl pkcs12 -info -in keystore.p12 -passin env:keystore_password

 curl -vvI https://es-elasticsearch-master:9200
 curl -vvI https://es-elasticsearch-master:9200 --cacert /etc/config/ca-keystore/ca.crt
 curl -vvI https://es-elasticsearch-master:9200 --cacert /etc/config/ca-keystore/ca.crt
 
openssl s_client -connect es-elasticsearch-master:9200 </dev/null 2>/dev/null | openssl x509 -inform pem -text
```

## Elastic Search Diagnositics
```
# Check Elastic Search Indexes available
curl -v --insecure --user elastic:123456 https://es-elasticsearch-master:9200/


curl -v --cacert /etc/config/ca-keystore/ca.crt --user elastic:123456 https://es-elasticsearch-master:9200/_cluster/health?wait_for_status=green&timeout=1s

curl -v --insecure --user elastic:123456 https://localhost:9200/_cluster/health?wait_for_status=green&timeout=1s

```

## Casandra Debugging.
```
tio.k.logs tio-temporal-schema-XXXXXXX -c setup-visibility-store
tio.k.logs tio-temporal-schema-XXXXXXX -c setup-visibility-store

curl --insecure -X PUT --fail --user elastic:cGFzc3dvcmRmb3JBQzgyNjI4MTMxWFJS https://es-elasticsearch-master:9200/_template/temporal_visibility_v1_template -H Content-Type: application/json --data-binary @schema/elasticsearch/visibility/index_template_v7.json

---- Updated server-job.yaml to echo value
curl --insecure -X PUT --fail --user elastic:cGFzc3dvcmRmb3JBQzgyNjI4MTMxWFJS https://es-elasticsearch-master:9200/_template/temporal_visibility_v1_template -H Content-Type: application/json --data-binary @schema/elasticsearch/visibility/index_template_v7.json
curl --insecure -X PUT --fail --user elastic:12345 https://es-elasticsearch-master:9200/temporal_visibility_v1_dev

 curl --insecure --user elastic:cGFzc3dvcmRmb3JBQzgyNjI4MTMxWFJS https://es-elasticsearch-master:9200/temporal_visibility_v1_dev


```


#### Skaffold , Over Helm and Port-Forward

```
# All Dependencies
skaffold dev --filename='skaffold-dependency.yaml'

skaffold render --filename='skaffold-temporal-io.yaml' > _temp_render_temporal.yaml

skaffold dev --filename='skaffold-temporal-io.yaml'

helm dependency update tio5-helm/

```



################################################################
################################################################
## ALTERNATE , MANUAL INSTALLATION without SKaffold
################################################################

```
### https://cert-manager.io/v1.1-docs/installation/kubernetes/
### TODO Merge all to single skaffold deployment.


# get the CRDS Definisions, Generic no need for Customization , just a wrapper
 kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.12.3/cert-manager.crds.yaml


# Setup Our Certs,issuers, Secrets
kubectl apply -f minikube-setup-secure-certcrds.yaml


kubectl get pods --namespace cert-manager



```


## For Deveopment use Skaffold, to deploy on minikube.

skaffold dev --filename='skaffold-xtemp-develop.yaml'

skaffold dev --filename='skaffold-xtemp-develop.yaml'
```
