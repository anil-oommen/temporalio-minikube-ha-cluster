#!/bin/bash
echo Temporal Cluster tio1-temporal-frontend http://192.168.1.205:15233/
kubectl port-forward --address 0.0.0.0 svc/tio1-temporal-frontend 15233:7233 -n default&