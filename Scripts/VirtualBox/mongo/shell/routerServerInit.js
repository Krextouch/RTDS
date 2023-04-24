sh.addShard("RTDSshard/192.168.10.201:27018");
sh.addShard("RTDSshard/192.168.10.202:27018");
sh.addShard("RTDSshard/192.168.10.203:27018");

sh.enableSharding("rtds");
sh.shardCollection("rtds.client", {"_id": "hashed"});
sh.shardCollection("rtds.database_sequences", {"_id": 1});
