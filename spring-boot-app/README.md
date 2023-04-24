## Spring Boot App with Temporal Workflows and Activties running. 

### Build Image on Docker & Deploy to minikube
```shell
docker images | grep spring-boot-app
docker image rm spring-boot-app:v2 spring-boot-app:latest 
sh gradlew jibDockerBuild

# Copy Image from Docker Local to minikube 
minikube image load spring-boot-app:latest -p kb8uk

# Deploy with helm chart
cd helm
helm install --atomic --timeout 120s app .
helm uninstall app
```


### Launching Workflows : 
 Open Port Forwards 
```shell
cd ../minikube-deploy/bin
sh  access-spring-boot-app.sh.sh
```
- Launch Workflows : http://192.168.1.205:28080/temporal/launch/tio1/10

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