package com.dastan.cake.data

data class ItemState(
    val listCake: List<CakeInfo> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)
