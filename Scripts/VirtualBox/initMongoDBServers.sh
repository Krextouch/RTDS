#!/usr/bin/env bash

sleep 5s
mongosh localhost:27019 /vagrant/mongo/shell/configServerInit.js
sleep 5s
mongosh localhost:27018 /vagrant/mongo/shell/shardServerInit.js
sleep 10s
mongosh localhost:27017 /vagrant/mongo/shell/routerServerInit.js
