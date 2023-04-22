
/* Debugging 
    
    kubectl run jumpbox --rm -it --image temporalio/admin-tools:1.20.1 -- bash
    curl http://elasticsearch-master:9200/temporal_visibility_v1_dev

tio1-temporal-es-index-setup
# Setup Indexs
curl -v http://elasticsearch-master-headless:9200
curl -v PUT --fail --user : http://elasticsearch-master-headless:9200/_template/temporal_visibility_v1_template -H "Content-Type: application/json" --data-binary "@schema/elasticsearch/visibility/index_template_v7.json" 
curl -v PUT --fail --user : http://elasticsearch-master-headless:9200/temporal_visibility_v1_dev 

curl -v PUT --user : \
http://elasticsearch-master-headless:9200/_template/temporal_visibility_v1_template \
-H "Content-Type: application/json" --data-binary \
"@schema/elasticsearch/visibility/index_template_v7.json"

curl -X PUT --fail --user : http://elasticsearch-master-headless:9200/temporal_visibility_v1_dev

curl -v PUT --user : http://elasticsearch-master-headless:9200/temporal_visibility_v1_dev


curl -X PUT --fail --user :
              http://elasticsearch-master-headless:9200/_template/temporal_visibility_v1_template
              -H "Content-Type: application/json" --data-binary
              "@schema/elasticsearch/visibility/index_template_v7.json" 2>&1 &&
              curl -X PUT --fail --user :
              http://elasticsearch-master-headless:9200/temporal_visibility_v1_dev
              2>&1
          resources: {}

curl -v PUT --fail --user : http://elasticsearch-master-headless:9200/_template/temporal_visibility_v1_template -H "Content-Type: application/json" --data-binary "@schema/elasticsearch/visibility/index_template_v7.json" 
curl -v PUT --fail --user : http://elasticsearch-master-headless:9200/temporal_visibility_v1_dev 







*/