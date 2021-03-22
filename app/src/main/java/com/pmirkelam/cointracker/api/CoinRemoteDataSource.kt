package com.pmirkelam.cointracker.api

import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinService: CoinService
) : BaseDataSource() {
    suspend fun fetchCoins() = getResult { coinService.getAllCoins() }
    suspend fun fetchCoin(id: String) = getResult { coinService.getCoin(id) }
}
