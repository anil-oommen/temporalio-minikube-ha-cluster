#!/bin/bash
echo SpringBoot App app-tio-spring http://192.168.1.205:28080/
kubectl port-forward --address 0.0.0.0 svc/app-tio-spring 28080:80 -n default&