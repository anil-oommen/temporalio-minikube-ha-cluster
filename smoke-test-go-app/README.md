
### Visual Studio Remote Extension for go Development
    settings.json
```json
    "go.goroot": "/home/anil/Development/install/go",
    "go.alternateTools": {
        "go": "/home/anil/Development/install/go/bin/go"
    }
```

## Runing on Local Build & Run
### Running Locally
```shell
./gradlew goBuild

./gradlew goTidy
./gradlew goTest
```
## Building Docker Image 
```shell
# build Docker Image
./gradlew buildImage
```


### Loading Image to Docker and running helm
```shell
# Copy Image from Docker Local to minikube 
minikube image load smoke-test-go-app:0.0.5 -p kb8uk

# Deploy with helm chart
helm install --atomic --timeout 90s --set image.tag=0.0.5 smoke.app helm
helm uninstall smoke.app
```


### https://wearenotch.com/way-to-go-combining-go-and-gradle/