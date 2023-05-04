/**
 * routerServerInit.js
 * mongosh script for initalizing the sharding functionality
 * author:     inf20133@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */

sh.addShard("RTDSshard1/n1:27018,n2:27018,n3:27018");
sh.addShard("RTDSshard2/n1:27020,n2:27020,n3:27020");

sh.enableSharding("rtds");
sh.shardCollection("rtds.client", {"_id": "hashed"});
sh.shardCollection("rtds.database_sequences", {"_id": 1});
