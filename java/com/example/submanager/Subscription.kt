package com.example.submanager

import java.io.Serializable

data class Subscription(
    var id: String = java.util.UUID.randomUUID().toString(),
    var name: String,
    var monthlyFee: Int,
    var paymentMethod: String
) : Serializable