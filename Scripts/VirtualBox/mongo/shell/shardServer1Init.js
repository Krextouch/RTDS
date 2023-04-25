rs.initiate(
    {
        _id: "RTDSshard1",
        members: [
            { _id: 0, host: "192.168.10.201:27018" },
            { _id: 1, host: "192.168.10.202:27018", priority: 2 },
            { _id: 2, host: "192.168.10.203:27018" },
        ]
    }
)
