rs.initiate(
    {
        _id: "RTDSshard2",
        members: [
            { _id: 0, host: "192.168.10.201:27020" },
            { _id: 1, host: "192.168.10.202:27020" },
            { _id: 2, host: "192.168.10.203:27020", priority: 2 },
        ]
    }
)
