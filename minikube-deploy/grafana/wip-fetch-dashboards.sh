#!/usr/bin/env bash

GRAFANA_USER="$(kubectl get secret --namespace default tio1-grafana -o jsonpath="{.data.admin-user}" | base64 -d)"
GRAFANA_PASSWORD="$(kubectl get secret --namespace default tio1-grafana -o jsonpath="{.data.admin-password}" | base64 -d)"
GRAFANA_HOST="http://192.168.1.205:18081"
echo "using Auth --user '${GRAFANA_USER}:${GRAFANA_PASSWORD}' " 



OUTPUT="$(curl  '${GRAFANA_HOST}/api/search?tag=temporal' --user '${GRAFANA_USER}:${GRAFANA_PASSWORD}' | jq -r '.[] | .uid')"
echo "-------------${OUTPUT}=="
for uid in `$(curl -s '${GRAFANA_HOST}/api/search?tag=temporal' --user '${GRAFANA_USER}:${GRAFANA_PASSWORD}' | jq -r '.[] | .uid')`; do
    echo "${uid}"
    dashboard="$(curl -s "${GRAFANA_HOST}/api/dashboards/uid/${uid}")"
    name=$(echo "${dashboard}" | jq -r '.meta.slug')
    echo "${dashboard}" | jq '.dashboard' > ${name}.json
done