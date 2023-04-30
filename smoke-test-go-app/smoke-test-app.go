package main

import (
	"fmt"
	"log"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "{status:'%s'}", "UP")
}

func main() {
	var listenPort = ":8080"
	fmt.Println("Running Smoke Test" + listenPort)
	http.HandleFunc("/health", handler)
	log.Fatal(http.ListenAndServe(listenPort, nil))
}
