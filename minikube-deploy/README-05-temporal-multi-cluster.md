## Install & Setup Temporal Cluster

### Deploy Cluster : **tio1**
```shell
cd tio1-helm
helm install -f values-tio1.yaml --atomic --timeout 30m tio1 .
helm uninstall tio1

# Other Commands
helm install -f values-tio1.yaml --atomic --timeout 15m tio1 .
helm install -f values-tio1.yaml --timeout 180s tio1 .
helm upgrade tio1 .

```

### Setup Namespace with Archival and Retrieval
 - To check archives : temporal-history-pod : /tmp/temporal_archival/development
```shell
# namespace 'fastns' with archive after 1 Day.
./bin/tctl --address 192.168.1.205:15233 --namespace fastns namespace register --global_namespace false --retention 1 --history_archival_state enabled
./bin/tctl --address 192.168.1.205:15233 --namespace fastns namespace update -vas enabled

# namespace others.
./bin/tctl --address 192.168.1.205:15233 --namespace default namespace register
./bin/tctl --address 192.168.1.205:15233 --namespace basic namespace register
# namespace help
./bin/tctl namespace register --gd 
```
### Add Custom Search Attributes 'AppReference'
```shell
./bin/tctl --address 192.168.1.205:15233 --namespace fastns cluster get-search-attributes
./bin/tctl --address 192.168.1.205:15233 --namespace fastns --auto_confirm admin cluster add-search-attributes --name AppReference --type Keyword

# to verify 
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow show --wid SWF008
```
### Query Samples
 - WorkflowId = "SWF001" or WorkflowId = "SWF002"  and AppReference = "APPREF001"

```shell
./bin/tctl --address 192.168.1.205:15233  wf listarchived -h
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow show --wid SWF008 --rid 0becde9b-6387-4329-b9e2-ea9353fd7fbf
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow listarchived -ps="20" -q "WorkflowId = 'SWF002'"
./bin/tctl --address 192.168.1.205:15233 --namespace fastns workflow listarchived -ps="20" -q "StartTime = '2023-01-31T00:00:00Z' AND SearchPrecision='Day' AND WorkflowType='NNNN'"
```


