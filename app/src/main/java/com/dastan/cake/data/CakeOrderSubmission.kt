package com.dastan.cake.data

import com.dastan.cake.data.model.CakeOrder

data class CakeOrderSubmission(
    val orders: List<CakeOrder>,
    val address: String,
    val name: String,
    val number: String
)
