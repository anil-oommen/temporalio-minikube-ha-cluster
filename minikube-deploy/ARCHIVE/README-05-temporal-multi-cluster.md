## Install & Setup Temporal Cluster

### (Option 1) Deploy Cluster with Postgres: **tio1-pg**
```shell
cd tio1-helm
helm install -f values-tio1-pg.yaml --atomic --timeout 30m tio1 .
helm uninstall tio1

# Other Commands
helm install -f values-tio1-pg.yaml --atomic --timeout 15m tio1 .
helm install -f values-tio1-pg.yaml --timeout 180s tio1 .
helm upgrade tio1 .

```

### (Option 1) Deploy Cluster with Cassandra: **tio1-cass**
```shell
cd tio1-helm
helm template -f values-tio1-cass.yaml . > out.yaml
helm install -f values-tio1-cass.yaml --atomic --timeout 30m tio1 .
helm upgrade -f values-tio1-cass.yaml --atomic --timeout 30m tio1 .
helm uninstall tio1

# Other Commands
helm install -f values-tio1-cass.yaml --atomic --timeout 15m tio1 .
helm install -f values-tio1-cass.yaml --timeout 180s tio1 .
helm upgrade tio1 .

```


### Startup the KubeCtl port-forwards for Temporal
```shell
cd ../bin
sh access-tio1-temporal-front.sh
sh access-tio1-temporal-web.sh
sh access-tio1-grafana.sh
sh access-prometheus.sh
```

### Setup Namespace with Archival and Retrieval
 - To check archives : temporal-history-pod : /tmp/temporal_archival/development
```shell
# namespace 'fastns' with archive after 1 Day.
./tctl --address 192.168.1.205:15233 --namespace fastns namespace register --global_namespace false --retention 1 --history_archival_state enabled
./tctl --address 192.168.1.205:15233 --namespace fastns namespace update -vas enabled

# namespace others.
./tctl --address 192.168.1.205:15233 --namespace default namespace register
./tctl --address 192.168.1.205:15233 --namespace basic namespace register
# namespace help
./bin/tctl namespace register --gd 
```
### Add Custom Search Attributes 'AppReference'
```shell
./tctl --address 192.168.1.205:15233 --namespace fastns cluster get-search-attributes
./tctl --address 192.168.1.205:15233 --namespace fastns --auto_confirm admin cluster add-search-attributes --name AppReference --type Keyword

# to verify 
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow show --wid SWF008
```

### Query Samples
 - WorkflowId = "SWF001" or WorkflowId = "SWF002"  and AppReference = "APPREF001"

```shell
./bin/tctl --address 192.168.1.205:15233  wf listarchived -h
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow show --wid SWF008 --rid 0becde9b-6387-4329-b9e2-ea9353fd7fbf 

# Query Workflows available
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow list -ps="20" -q "WorkflowId = 'SWF002'"
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow list -ps="20" -q "WorkflowType = 'SimpleWorkflow' AND StartTime > '2023-01-31T00:00:00Z'"
# Query Workflows archived
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow listarchived -ps="20" -q "WorkflowId = 'SWF002'"

./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow listarchived -ps="20" -q "StartTime = '2023-01-31T00:00:00Z' AND SearchPrecision='Day' AND WorkflowType='NNNN'"
```

# 
```
./bin/tctl --address 192.168.1.205:15233 --namespace fastns  wf desc
```
