package web

import (
	"fmt"
	"log"
	"net/http"
)

func HealthHttpHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "{status:'%s'}", "UP")
}

func StartServer(listenPort string) {
	fmt.Println("Starting Server:" + listenPort)
	http.HandleFunc("/health", HealthHttpHandler)
	log.Fatal(http.ListenAndServe(listenPort, nil))
}
