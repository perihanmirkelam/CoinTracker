package com.pmirkelam.cointracker.data

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val hashing: String?,
    val description: Description,
    val image: Image,
    val currentPrice: String,
    val priceChangePercentage: Float,
    var interval: Int = -1,
    var favorited: Boolean = false
)

data class Description(
    val en: String?
)

data class Image(
    val thumb: String,
    val small: String,
    val large: String
)