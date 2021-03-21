package com.pmirkelam.cointracker.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coin_details")
data class CoinDetail(
    @PrimaryKey
    val id: String,
    val symbol: String,
    var name: String,
    @field:SerializedName("hashing_algorithm")
    var hashing: String?,
    @Embedded
    @field:SerializedName("market_data")
    var marketData: MarketData?,
    var description: Description?,
    var image: Image?,
    val interval: Int?,
    val favorited: Boolean?
)

data class Description(var en: String? = "")

data class Image(var small: String? = "", var large: String? = "")

data class MarketData(
    @Embedded
    @field:SerializedName("current_price")
    var currentPrice: CurrentPrice? = null,
    @field:SerializedName("price_change_percentage_24h")
    var priceChangePercentage: Double? = null,
) {
    data class CurrentPrice(var usd: Float? = -1F)
}


