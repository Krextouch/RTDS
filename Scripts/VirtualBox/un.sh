#!/usr/bin/env bash

# Add mongoDB repository
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | sudo apt-key add -
sudo apt-get install gnupg
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
sudo apt-get update

# Install and run mongoDB
sudo apt install -y mongodb-org
sudo sed -i 's/127.0.0.1/0.0.0.0/' /etc/mongod.conf
sudo systemctl start mongod
sudo systemctl daemon-reload
