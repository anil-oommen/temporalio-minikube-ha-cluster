
### Visual Studio Remote Extension for go Development
    settings.json
```json
    "go.goroot": "/home/anil/Development/install/go",
    "go.alternateTools": {
        "go": "/home/anil/Development/install/go/bin/go"
    }
```

## Managing Dependencies
```shell
# add 3rd party modules
go get "go.temporal.io/sdk/client"
# cleanup 3rd party modules if not used in .go
go mod tidy
```

## Runing on Local Build & Run
### Running Locally
```shell
./gradlew goBuild

./gradlew goTidy
./gradlew goTest

go run cmd/server/main.go

```
## Building Docker Image 
```shell
# build Docker Image
./gradlew buildImage
```


### Loading Image to Docker and running helm
```shell
# Copy Image from Docker Local to minikube 
minikube image load smoke-test-go-app:0.0.6 -p kb8uk

# Deploy with helm chart
helm install --atomic --timeout 90s --set image.tag=0.0.6 smoke.app helm
helm uninstall smoke.app
```
