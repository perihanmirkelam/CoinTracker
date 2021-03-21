package com.pmirkelam.cointracker.utils

import androidx.room.TypeConverter
import com.pmirkelam.cointracker.data.Description
import com.pmirkelam.cointracker.data.Image
import com.pmirkelam.cointracker.data.MarketData

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toDescription(en: String?): Description? {
            return en?.let { Description(it) }
        }

        @TypeConverter
        @JvmStatic
        fun fromDescriptionToString(description: Description?): String? {
            return description?.en
        }

        @TypeConverter
        @JvmStatic
        fun toImage(url: String?): Image? {
            return url?.let { Image(large = it) }
        }

        @TypeConverter
        @JvmStatic
        fun fromImageToString(image: Image?): String? {
            return image?.large
        }

        @TypeConverter
        @JvmStatic
        fun fromPercentageToMarketData(percentage: Double?): MarketData? {
            return percentage?.let { MarketData(priceChangePercentage = it) }
        }

        @TypeConverter
        @JvmStatic
        fun fromPercentageToString(marketData: MarketData?): Double? {
            return marketData?.priceChangePercentage
        }

        @TypeConverter
        @JvmStatic
        fun toPrice(price: Float?): MarketData? {
            return price?.let { MarketData(currentPrice = MarketData.CurrentPrice(it)) }
        }

        @TypeConverter
        @JvmStatic
        fun fromPriceToString(marketData: MarketData?): Float? {
            return marketData?.currentPrice?.usd
        }

    }
}