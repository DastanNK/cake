package com.dastan.cake.data

data class CakeOrderSubmission(
    val orders: List<CakeOrder>,
    val address: String,
    val name: String,
    val number: String
)
