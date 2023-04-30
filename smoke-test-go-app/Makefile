tidyGo:
	env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go mod tidy -v
cleanGo:
	go clean -cache
buildGo:
	$(info *************************************)
	env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go build -o ${PROJECT_BUILD_DIR}/${GOOS}-${GOARCH}/${PROJECT_NAME} cmd/server/main.go
testGo:
	# env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go test .
	# env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go test ./...
	env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go test -v -coverpkg=./... -coverprofile=build/coverprofile.cov ./...
	env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go tool cover -html build/coverprofile.cov -o build/coverprofile.html
modListGo:
	env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=0 go list -m all