## Spring Boot App with Temporal Workflows and Activties running. 

### Build Image on Docker & Deploy to minikube
```shell
docker images
docker image rm spring-boot-app:v2
docker image rm spring-boot-app:latest
sh gradlew jibDockerBuild

# Copy Image from Docker Local to minikube 
minikube image load spring-boot-app:v2 -p mknode

# Deploy with helm chart
cd helm
helm install --atomic --timeout 120s app .
helm uninstall app
```

### Working just with Docker
```shell
docker image rm spring-boot-app:v2
# Try Runining in Docker
docker run --name tio-spring-tryout -it spring-boot-app:v2
```


### No Longer Viable
```shell
docker load --input jib-image.tar
```
