package com.pmirkelam.cointracker.data

import com.pmirkelam.cointracker.data.db.CoinDAO
import com.pmirkelam.cointracker.data.network.CoinRemoteDataSource
import com.pmirkelam.cointracker.utils.resultLiveData
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource,
    private val localDataSource: CoinDAO,
) {

    fun getCoin(id: String) = resultLiveData(
        databaseQuery = { localDataSource.getCoin(id) },
        networkCall = { remoteDataSource.fetchCoin(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getCoins() = resultLiveData(
        databaseQuery = { localDataSource.getAllCoins() },
        networkCall = { remoteDataSource.fetchCoins() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

}