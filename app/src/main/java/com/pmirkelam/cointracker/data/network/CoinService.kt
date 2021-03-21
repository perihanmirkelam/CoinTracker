package com.pmirkelam.cointracker.data.network

import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.data.CoinDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService {
    @GET("coins/list")
    suspend fun getAllCoins(): Response<List<Coin>>

    @GET("coins/{id}")
    suspend fun getCoin(@Path("id") id: String): Response<CoinDetail>
}