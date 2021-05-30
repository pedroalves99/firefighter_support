#!/bin/bash
set -e

MODULES=(
	rest-service
	grafana
)

for i in ${!MODULES[@]}; do
	cd ${MODULES[$i]}

	rm -rf target	
	mvn -U install
	mvn -X -Dmaven.test.skip=true --settings ../settings.xml deploy
	cd ..
done
