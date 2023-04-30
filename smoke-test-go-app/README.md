
### Visual Studio Remote Extension for go Development
    settings.json
```json
    "go.goroot": "/home/anil/Development/install/go",
    "go.alternateTools": {
        "go": "/home/anil/Development/install/go/bin/go"
    }
```


### Running Locally
```shell
go run .
```

### MultiStage Build for Smaller Image size. 
```shell
docker images | grep smoke-test-app
docker image rm smoke-test-app:slim 

docker build -t smoke-test-app:slim -f Dockerfile .
```

### Loading Image to Docker and running helm
```shell
# Copy Image from Docker Local to minikube 
minikube image load smoke-test-app:slim -p kb8uk

# Deploy with helm chart
cd helm
helm install --atomic --timeout 30s smoke.app .
helm uninstall smoke.app
```