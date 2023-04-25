#!/usr/bin/env bash

# Set IP-address of Nodes
n1="192.168.10.201"
n2="192.168.10.202"
n3="192.168.10.203"

# Edit files
sed -i 's/n1/'${n1}'/' /vagrant/mongo/config/routerServer.yaml
sed -i 's/n2/'${n2}'/' /vagrant/mongo/config/routerServer.yaml
sed -i 's/n3/'${n3}'/' /vagrant/mongo/config/routerServer.yaml

sed -i 's/n1/'${n1}'/' /vagrant/mongo/shell/configServerInit.js
sed -i 's/n2/'${n2}'/' /vagrant/mongo/shell/configServerInit.js
sed -i 's/n3/'${n3}'/' /vagrant/mongo/shell/configServerInit.js

sed -i 's/n1/'${n1}'/' /vagrant/mongo/shell/routerServerInit.js
sed -i 's/n2/'${n2}'/' /vagrant/mongo/shell/routerServerInit.js
sed -i 's/n3/'${n3}'/' /vagrant/mongo/shell/routerServerInit.js

sed -i 's/n1/'${n1}'/' /vagrant/mongo/shell/shardServer1Init.js
sed -i 's/n2/'${n2}'/' /vagrant/mongo/shell/shardServer1Init.js
sed -i 's/n3/'${n3}'/' /vagrant/mongo/shell/shardServer1Init.js

sed -i 's/n1/'${n1}'/' /vagrant/mongo/shell/shardServer2Init.js
sed -i 's/n2/'${n2}'/' /vagrant/mongo/shell/shardServer2Init.js
sed -i 's/n3/'${n3}'/' /vagrant/mongo/shell/shardServer2Init.js


# Install gnupg
sudo apt-get install gnupg

# Add Eclipse Adoptium repository to apt
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo apt-key add -
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list

# Add mongoDB repository to apt
curl -fsSL https://pgp.mongodb.com/server-6.0.asc | \
   sudo gpg -o /usr/share/keyrings/mongodb-server-6.0.gpg \
   --dearmor
get -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | sudo apt-key add -
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-6.0.gpg ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
sudo apt-get update

# Install Java
sudo apt install -y temurin-17-jdk

# Install mongoDB
sudo apt install -y mongodb-org

# Create mongoDB dbpath
sudo mkdir /var/lib/mongodb/configS
sudo mkdir /var/lib/mongodb/routerS
sudo mkdir /var/lib/mongodb/shardS1
sudo mkdir /var/lib/mongodb/shardS2

# Run mongod instances
sudo mongod --config /vagrant/mongo/config/configServer.yaml &
sudo mongod --config /vagrant/mongo/config/shardServer1.yaml &
sudo mongod --config /vagrant/mongo/config/shardServer2.yaml &
sudo mongos --config /vagrant/mongo/config/routerServer.yaml &
sleep 5s

# Install gradle
sudo apt install -y unzip
sudo mkdir /tmp/gradle
cd /tmp/gradle
sudo wget https://services.gradle.org/distributions/gradle-8.1-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.1-bin.zip
export PATH=$PATH:/opt/gradle/gradle-8.1/bin

# Clone github repository
sudo mkdir /opt/RTDSnode
cd /opt/RTDSnode
RTDSbranch="main"
url="https://github.com/Krextouch/RTDS/archive/${RTDSbranch}.tar.gz"
sudo curl -L "${url}" -o RTDS.tar.gz
sudo tar -xvzf RTDS.tar.gz --strip-components=1 RTDS-${RTDSbranch}/Node
sudo rm RTDS.tar.gz
cd Node/src/main/resources

# Set mongoDB URI in Spring Properties
sed -i 's/n1/'${n1}'/' ./application.properties
sed -i 's/n2/'${n2}'/' ./application.properties
sed -i 's/n3/'${n3}'/' ./application.properties

# Get version number
cd /opt/RTDSnode/Node
version=$(cat ./build.gradle | grep "^version" | cut -d"=" -f2)
version=${version//\'/} #Trim apostrophes
version=${version// /} #Trim white spaces

# Build gradle app
gradle clean build

# Run gradle app
cd build/libs
sudo java -jar Node-"${version}".jar maxX=1000 maxY=1000 &
