#!/bin/bash
echo Temporal Cluster tio1-temporal-web http://192.168.1.205:15092/
kubectl port-forward --address 0.0.0.0 svc/tio1-temporal-web 15092:8080 -n default&