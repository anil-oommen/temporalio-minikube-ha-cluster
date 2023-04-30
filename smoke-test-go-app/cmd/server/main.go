package main

import (
	"oom/temporal.io/smoke-test-app/pkg/web"
)

func main() {
	web.StartServer(":8080")
}
