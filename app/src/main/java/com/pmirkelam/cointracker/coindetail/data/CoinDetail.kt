package com.pmirkelam.cointracker.coindetail.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable {

    companion object {
        fun DocumentSnapshot.toCoinDetail(): CoinDetail? {
            val id = getString("id")
            val name = getString("name")
            val symbol = getString("symbol")

            return if (id.isNullOrEmpty() || name.isNullOrEmpty() || symbol.isNullOrEmpty()) null
            else CoinDetail(id, name, symbol, null, null, null, null, null)
        }
    }
}

@Parcelize
data class Description(
    @ColumnInfo(name = "description")
    val en: String?
) : Parcelable

@Parcelize
data class Image(
    @SerializedName("large")
    val url: String?
) : Parcelable

@Parcelize
data class MarketData(
    @Embedded
    @SerializedName("current_price")
    val currentPrice: CurrentPrice?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?
) : Parcelable

@Parcelize
data class CurrentPrice(
    @ColumnInfo(name = "price")
    val usd: Float?
) : Parcelable

