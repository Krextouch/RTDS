#!/usr/bin/env bash

# Install gnupg
sudo apt-get install gnupg

# Add Eclipse Adoptium repository to apt
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo apt-key add -
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list

# Add mongoDB repository to apt
#wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | sudo apt-key add -
#echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
sudo apt-get update

# Install Java
sudo apt install -y temurin-17-jdk

# Install mongoDB
#sudo apt install -y mongodb-org

# Create mongoDB dbpath
sudo mkdir /var/lib/mongodb/configS
sudo mkdir /var/lib/mongodb/routerS
sudo mkdir /var/lib/mongodb/shardS

# Run mongod instances
#sudo mongod --config /vagrant/mongo/config/configServer.config &
sleep 5s

# Install gradle
sudo apt install -y unzip
sudo mkdir /tmp/gradle
cd /tmp/gradle
sudo wget https://services.gradle.org/distributions/gradle-8.1-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.1-bin.zip
export PATH=$PATH:/opt/gradle/gradle-8.1/bin

# Clone github repository, build and run node app
sudo mkdir /opt/RTDSnode
cd /opt/RTDSnode
RTDSbranch="main"
# TODO:
#sudo curl -L https://github.com/Krextouch/RTDS/archive/$RTDSbranch -o RTDS.tar.gz
#sudo tar -xvzf RTDS.tar.gz --strip-components=1 RTDS-$RTDSbranch/Node
#gradle clean build
