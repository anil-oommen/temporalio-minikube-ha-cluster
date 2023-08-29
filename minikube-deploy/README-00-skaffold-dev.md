
# init , skaffold installed with gcloud.
```
skaffold config set --global local-cluster true
skaffold init --skip-build

```

## Cert Manager
#### https://cert-manager.io/docs/installation/helm/
```
helm repo add jetstack https://charts.jetstack.io
helm search repo jetstack
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
```

## Utility Container
```
# Commands from Utility, to diagnize the certificate.
# for Containers with Volume Mount.
 kubectl apply -f minikube-utility-shell.yaml
 kubectl exec --stdin --tty shellbox -- /bin/bash
 cd /etc/options/...

 # https://www.sslshopper.com/article-most-common-openssl-commands.html
 openssl x509 -in tls.crt -text -noout
 # Verify Cert without Trusting CA
 openssl verify tls.crt
 # Verify Cert Providing CA
 openssl verify -CAfile ca.crt tls.crt
 openssl verify -CAfile ../ca-keystore/ca.crt tls.crt


 curl -vvI https://es-elasticsearch-master:9200
 openssl s_client -connect es-elasticsearch-master:9200 </dev/null 2>/dev/null | openssl x509 -inform pem -text
```

## Elastic Search Diagnositics
```
# Check Elastic Search Indexes available
curl -v --insecure --user elastic:123456 https://es-elasticsearch-master:9200/


curl -v --insecure --user elastic:123456 https://es-elasticsearch-master:9200/_cluster/health?wait_for_status=green&timeout=1s
```


#### Skaffold , Over Helm and Port-Forward

```
# All Dependencies
skaffold dev --filename='skaffold-dependency.yaml'
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
```
