
## Postgres Setup
- Single Instance for all Clusters.
- database & schema setup by temporal charts as needed. 
- Source : [bitnami/postgres](https://github.com/bitnami/charts/tree/main/bitnami/postgresql/#installing-the-chart)

```shell
cd ../cass-helm
helm install -f values-cass.yaml --atomic --timeout 30m cass .
helm uninstall cass

```
####  


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

## Install Cassandra Schema
https://github.com/temporalio/helm-charts
```
kubectl run --namespace default temporal-admin-tool --rm --tty -i --restart='Never' \
   --image temporalio/admin-tools:1.20.1 -- bash


export CASSANDRA_HOST=cass-cassandra
export CASSANDRA_PORT=9042
export CASSANDRA_USER=cassandra
export CASSANDRA_PASSWORD=cGFzc3dvcmRmb3JBQzgyNjI4MTMxWFJS

temporal-cassandra-tool create-Keyspace -k temporal
CASSANDRA_KEYSPACE=temporal temporal-cassandra-tool setup-schema -v 0.0
CASSANDRA_KEYSPACE=temporal temporal-cassandra-tool update -schema-dir schema/cassandra/temporal/versioned

temporal-cassandra-tool create-Keyspace -k temporal_visibility
CASSANDRA_KEYSPACE=temporal_visibility temporal-cassandra-tool setup-schema -v 0.0
CASSANDRA_KEYSPACE=temporal_visibility temporal-cassandra-tool update -schema-dir schema/cassandra/visibility/versioned

```