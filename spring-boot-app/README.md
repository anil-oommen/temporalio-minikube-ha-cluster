## Spring Boot App with Temporal Workflows and Activities running. 

## Local Development
 * use Local Temporallite.
 * [Launch Cancellable Workflow](http://localhost:8081/temporal/launch-cancellable/tio1/1)
 * [Launch Loaded Workflow](http://localhost:8081/temporal/launch-loaded/tio1/1)
```shell
temporalite.exe start -namespace fastns
```

## Deploy on Minikube
### Build Image on Docker & Deploy to minikube
```shell
docker images | grep spring-boot-app
docker image rm spring-boot-app:v2 spring-boot-app:latest 
sh gradlew jibDockerBuild

# Copy Image from Docker Local to minikube 
minikube image load spring-boot-app:latest -p kb8uk

# Deploy with helm chart
cd helm
helm install --atomic --timeout 4m app .
helm uninstall app
```


### Launching Workflows : 
 Open Port Forwards 
```shell
cd ../minikube-deploy/bin
sh  access-spring-boot-app.sh
```
- Launch Workflows : http://192.168.1.205:28080/temporal/launch-loaded/tio1/10
- Launch Workflows : http://192.168.1.205:28080/temporal/launch-cancellable/tio1/10

### (Alternative Not using Minikube) Working just with Docker
```shell
docker image rm spring-boot-app:v2
# Try Runining in Docker
docker run --name tio-spring-tryout -it spring-boot-app:v2
```


### No Longer Viable
```shell
docker load --input jib-image.tar
```
### Git Push with SSH Key
```shell
git remote set-url origin git@github.com:anil-oommen/temporalio-minikube-ha-cluster.git
```

