#!/bin/bash
echo Grafana tio1-grafana http://192.168.1.205:18081/   admin:{password=}
echo $(kubectl get secret --namespace default tio1-grafana -o jsonpath="{.data.admin-password}" | base64 -d) 
kubectl port-forward --address 0.0.0.0 svc/tio1-grafana 18081:80 -n default&