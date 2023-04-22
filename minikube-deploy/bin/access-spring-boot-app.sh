#!/bin/bash
echo SpringBoot App spring-boot-app http://192.168.1.205:28080/
kubectl port-forward --address 0.0.0.0 svc/spring-boot-app 28080:80 -n default&