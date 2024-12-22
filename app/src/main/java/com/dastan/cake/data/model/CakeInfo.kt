package com.dastan.cake.data.model

data class CakeResponse(
    val cake: List<CakeInfo>?= emptyList()
)

data class CakeInfo(
    val id: String?,
    val title: String?,
    val description: String?,
    val price: Price?,
    val weight: Weight?,
    val image:String

)

data class Price(
    val priceSmall: String?,
    val priceMedium: String?,
    val priceBig: String?
)

data class Weight(
    val weightSmall: String?,
    val weightMedium: String?,
    val weightBig: String?
)