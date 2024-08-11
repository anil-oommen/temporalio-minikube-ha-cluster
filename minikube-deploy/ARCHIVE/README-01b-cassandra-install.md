
## Cassandra Setup
 - As Independent Service, Helm Charts from https://artifacthub.io/packages/helm/bitnami/cassandra
 - https://github.com/bitnami/charts/tree/main/bitnami/cassandra
 - https://pwittrock.github.io/docs/tutorials/stateful-application/cassandra/
 - https://artifacthub.io/packages/helm/bitnami/cassandra


### Add Repo and check is available.
```
helm repo add bitnami-repo https://charts.bitnami.com/bitnami
helm search repo cassandra
```

### Post Scaffold Deployment Check Client Access.
```
# Connecting with Cassandra Client.
export CASSANDRA_PASSWORD=$(kubectl get secret --namespace "default" single-use-dev-secrets -o jsonpath="{.data.cassandra-password}" | base64 -d)

kubectl run --namespace default cass-cassandra-client --rm --tty -i --restart='Never' \
   --env CASSANDRA_PASSWORD=$CASSANDRA_PASSWORD \
   --image docker.io/bitnami/cassandra:4.1.3-debian-11-r24 -- bash

cqlsh -u cassandra -p $CASSANDRA_PASSWORD cass-cassandra

```



### Debugging Why temporal-schema-update would fail
 - Mostly can fail with "400, Bad Request "resource_already_exists_exception","reason"
 - current scripts on smart enough.
 - if so use schema.setup.enabled.


```
kubectl run --namespace default tio-admin-client --rm --tty -i --restart='Never' \
   --image temporalio/admin-tools:1.21.5 -- bash

export CASSANDRA_HOST=cass-cassandra.default.svc.cluster.local
export CASSANDRA_KEYSPACE=temporal
export CASSANDRA_PASSWORD=cGFzc3dvcmRmb3JBQzgyNjI4MTMxWFJS
export CASSANDRA_PORT=9042
export CASSANDRA_USER=cassandra

temporal-cassandra-tool update-schema --schema-dir /etc/temporal/schema/cassandra/temporal/versioned


```




## ##################################################
# OUTDATED DO NOT USE Only for reference


```bash
cd ../cass-helm

#### Using Helm 

```shell

helm install -f values-cass.yaml --atomic --timeout 30m cass .
helm install -f values-cass.yaml --atomic --timeout 30m --set debug.enabled=true,image.debug=true cass .


helm uninstall cass

## Helm Commands
helm upgrade -f values-cass.yaml --atomic --timeout 30m cass .
helm upgrade --dry-run -f values-cass.yaml --atomic --timeout 30m cass .



helm plugin install https://github.com/databus23/helm-diff
helm history cass

# get diffs 
helm diff upgrade -f values-cass.yaml cass .
helm diff revision cass 1 2

# without Helm
kubectl scale statefulset cass-cassandra --replicas=5


kubectl cordon kb8uk-m02
kubectl drain kb8uk-m02

```
 




### IMPORTANT Password stored in Persistant Volume, so have to delete if you are recreating cluster

```
# get the password, use command generated during install.
export CASSANDRA_PASSWORD=.... 

echo $CASSANDRA_PASSWORD
```
## Test Connection using a cassandra client .
```
kubectl run --namespace default cass-cassandra-client --rm --tty -i --restart='Never' \
   --env CASSANDRA_PASSWORD=$CASSANDRA_PASSWORD \
   --image docker.io/bitnami/cassandra:4.1.2-debian-11-r1 -- bash

cqlsh -u cassandra -p $CASSANDRA_PASSWORD cass-cassandra

```
## Connect from outside

```
    kubectl port-forward --namespace default svc/cass-cassandra 9042:9042 &
    cqlsh -u cassandra -p $CASSANDRA_PASSWORD 127.0.0.1 9042

```

## Install Temporal Schema on Cassandra
https://github.com/temporalio/helm-charts
```
kubectl run --namespace default temporal-admin-tool --rm --tty -i --restart='Never' \
   --image temporalio/admin-tools:1.20.1 -- /bin/bash
   


export CASSANDRA_HOST=cass-cassandra
export CASSANDRA_PORT=9042
export CASSANDRA_USER=cassandra
export CASSANDRA_PASSWORD=

temporal-cassandra-tool create-Keyspace -k temporal
CASSANDRA_KEYSPACE=temporal temporal-cassandra-tool setup-schema -v 0.0
CASSANDRA_KEYSPACE=temporal temporal-cassandra-tool update -schema-dir schema/cassandra/temporal/versioned

temporal-cassandra-tool create-Keyspace -k temporal_visibility
CASSANDRA_KEYSPACE=temporal_visibility temporal-cassandra-tool setup-schema -v 0.0
CASSANDRA_KEYSPACE=temporal_visibility temporal-cassandra-tool update -schema-dir schema/cassandra/visibility/versioned

```

## Cassandra Common Commands
```
# List KeySpace and tables.
DESCRIBE KEYSPACE temporal_visibility;
DESCRIBE KEYSPACE temporal;



```