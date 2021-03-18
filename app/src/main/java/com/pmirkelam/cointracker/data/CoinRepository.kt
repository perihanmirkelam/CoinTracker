package com.pmirkelam.cointracker.data

import androidx.lifecycle.MutableLiveData
import javax.inject.Singleton

@Singleton
object CoinRepository {

    private val coins: List<Coin> =
        mutableListOf(
            Coin(
                "asd",
                "BTC",
                "Bitcoin",
                "ad",
                Description("asd"),
                Image("asd", "asd", "asd"),
                "$10.000.000",
                1.0F,
                0,
                false
            )
        )
    val coinsLiveData: MutableLiveData<List<Coin>> = MutableLiveData<List<Coin>>(coins)

    fun getCoins() {
        return
    }
}