#!/bin/bash
echo Promentheus pr-prometheus-server http://192.168.1.205:18080/
kubectl port-forward --address 0.0.0.0 svc/pr-prometheus-server 18080:80 -n default&
