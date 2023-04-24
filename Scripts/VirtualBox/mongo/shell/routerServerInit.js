sh.addShard("rtds/192.168.10.201:27018");
sh.addShard("rtds/192.168.10.202:27018");
sh.addShard("rtds/192.168.10.203:27018");

sh.enableSharding("rtds");
sh.shardCollection("rtds.MoveRequest", {"clientId":1});
