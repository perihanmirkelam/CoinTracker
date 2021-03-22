package com.pmirkelam.cointracker.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coin_details")
data class CoinDetail(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("hashing_algorithm")
    val hashing: String?,
    @Embedded
    @SerializedName("market_data")
    val marketData: MarketData?,
    @Embedded
    val description: Description?,
    @Embedded
    val image: Image?,
    val interval: Int?,
    var favorited: Boolean?
)

data class Description(
    @ColumnInfo(name = "description")
    val en: String?
)

data class Image(
    @SerializedName("large")
    val url: String?
)

data class MarketData(
    @Embedded
    @SerializedName("current_price")
    val currentPrice: CurrentPrice?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?
)

data class CurrentPrice(
    @ColumnInfo(name = "price")
    val usd: Float?
)


