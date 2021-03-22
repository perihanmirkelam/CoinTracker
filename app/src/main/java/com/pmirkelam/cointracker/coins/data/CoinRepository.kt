package com.pmirkelam.cointracker.coins.data

import com.pmirkelam.cointracker.api.CoinRemoteDataSource
import com.pmirkelam.cointracker.utils.Resource
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource,
    private val coinDAO: CoinDAO,
) {

    suspend fun getPagedCoins(offset: Int): List<Coin> {
        val cachedCoins = coinDAO.get4Coins(offset)
        if (cachedCoins.isEmpty()) {
            fetchCoins()
        }
        return coinDAO.get4Coins(offset)
    }

    suspend fun fetchCoins(){
        val resource = remoteDataSource.fetchCoins()
        if (resource.status == Resource.Status.SUCCESS){
            resource.data?.let {
                coinDAO.insertAll(resource.data)
            }
        }
    }
}