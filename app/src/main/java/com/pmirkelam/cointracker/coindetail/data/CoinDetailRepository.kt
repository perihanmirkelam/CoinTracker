package com.pmirkelam.cointracker.coindetail.data

import com.pmirkelam.cointracker.api.CoinRemoteDataSource
import com.pmirkelam.cointracker.utils.resultLiveData
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource,
    private val coinDetailDAO: CoinDetailDAO,
) {

    fun getCoin(id: String) = resultLiveData(
        databaseQuery = { coinDetailDAO.getCoin(id) },
        networkCall = { remoteDataSource.fetchCoin(id) },
        saveCallResult = { coinDetailDAO.insert(it) }
    )

}