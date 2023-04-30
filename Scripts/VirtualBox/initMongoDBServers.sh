#!/usr/bin/env bash

sleep 5s
mongosh localhost:27019 /opt/RTDSnode/mongo/shell/configServerInit.js
sleep 5s
mongosh localhost:27018 /opt/RTDSnode/mongo/shell/shardServer1Init.js
sleep 5s
mongosh localhost:27020 /opt/RTDSnode/mongo/shell/shardServer2Init.js
sleep 30s
mongosh localhost:27017 /opt/RTDSnode/mongo/shell/routerServerInit.js
