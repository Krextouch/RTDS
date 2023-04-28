#!/usr/bin/env bash

sleep 5s
mongosh localhost:27019 /vagrant/mongo/shell/configServerInit.js
sleep 5s
mongosh localhost:27018 /vagrant/mongo/shell/shardServer1Init.js
sleep 5s
mongosh localhost:27020 /vagrant/mongo/shell/shardServer2Init.js
sleep 30s
mongosh localhost:27017 /vagrant/mongo/shell/routerServerInit.js
