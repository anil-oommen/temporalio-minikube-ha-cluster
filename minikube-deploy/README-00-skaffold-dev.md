
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
