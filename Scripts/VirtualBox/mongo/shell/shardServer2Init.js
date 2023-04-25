rs.initiate(
    {
        _id: "RTDSshard2",
        members: [
            { _id: 0, host: "n1:27020" },
            { _id: 1, host: "n2:27020" },
            { _id: 2, host: "n3:27020", priority: 2 },
        ]
    }
)
