rs.initiate(
    {
        _id: "RTDSshard1",
        members: [
            { _id: 0, host: "n1:27018" },
            { _id: 1, host: "n2:27018", priority: 2 },
            { _id: 2, host: "n3:27018" },
        ]
    }
)
