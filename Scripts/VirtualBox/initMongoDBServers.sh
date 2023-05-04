#!/usr/bin/env bash

# initMongoDBServer.sh
# Automatic mongoDB initialisation script
# author:     inf20133@lehre.dhbw-stuttgart.de
# date:       04.05.2023
# version:    1.0.0

sleep 5s
mongosh localhost:27019 /opt/RTDSnode/mongo/shell/configServerInit.js
sleep 5s
mongosh localhost:27018 /opt/RTDSnode/mongo/shell/shardServer1Init.js
sleep 5s
mongosh localhost:27020 /opt/RTDSnode/mongo/shell/shardServer2Init.js
sleep 30s
mongosh localhost:27017 /opt/RTDSnode/mongo/shell/routerServerInit.js
