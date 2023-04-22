

helm dependencies update

cd tio1-helm-charts
helm install -f values-tio1.yaml --atomic --timeout 30m tio1 .
helm install -f values-tio1.yaml --atomic --timeout 15m tio1 .
helm install -f values-tio1.yaml --timeout 180s tio1 .
helm upgrade tio1 .
helm uninstall tio1


/***************************

## Setup Namesapce Archival and Retrieval
./tctl --address 192.168.1.205:15233 --namespace fastns namespace register --global_namespace false --retention 1 --history_archival_state enabled
./tctl --address 192.168.1.205:15233 --namespace fastns namespace update -vas enabled

./tctl --address 192.168.1.205:15233 --namespace default namespace register
./tctl --address 192.168.1.205:15233 --namespace basic namespace register

/***************************
Custom Search Attributes
./tctl --address 192.168.1.205:15233 --namespace fastns cluster get-search-attributes
./tctl --address 192.168.1.205:15233 --namespace fastns --auto_confirm admin cluster add-search-attributes --name AppReference --type Keyword

./tctl --address 192.168.1.205:15233 --namespace fastns workflow show --wid SWF008
# Query : WorkflowId = "SWF001" or WorkflowId = "SWF002"  and AppReference = "APPREF001"
/*************************** 

## Name space Creation get Help 
# ./tctl namespace register --gd 

temporal-history-pod : /tmp/temporal_archival/development

./tctl --address 192.168.1.205:15233  wf listarchived -h
./tctl --address 192.168.1.205:15233 --namespace fastns workflow show --wid SWF008 --rid 0becde9b-6387-4329-b9e2-ea9353fd7fbf
./tctl --address 192.168.1.205:15233 --namespace fastns workflow listarchived -ps="20" -q "WorkflowId = 'SWF002'"
./tctl --address 192.168.1.205:15233 --namespace fastns workflow listarchived -ps="20" -q "StartTime = '2023-01-31T00:00:00Z' AND SearchPrecision='Day' AND WorkflowType='HelloWorldWorkflowID'"