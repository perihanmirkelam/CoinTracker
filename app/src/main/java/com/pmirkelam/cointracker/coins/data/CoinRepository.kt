package com.pmirkelam.cointracker.coins.data

import com.pmirkelam.cointracker.api.CoinDataSource
import com.pmirkelam.cointracker.utils.Constants.PAGE_SIZE
import com.pmirkelam.cointracker.utils.Resource
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val dataSource: CoinDataSource,
    private val coinDAO: CoinDAO,
) {

    suspend fun getPagedCoins(offset: Int): List<Coin> {
        val cachedCoins = coinDAO.getPagedCoins(offset, PAGE_SIZE)
        if (cachedCoins.isEmpty()) {
            fetchCoins()
        }
        return coinDAO.getPagedCoins(offset, PAGE_SIZE)
    }

    suspend fun getFilteredPagedCoins(filter: String, offset: Int): List<Coin> {
        val cachedCoins = coinDAO.getFilteredCoins("$filter%", offset, PAGE_SIZE)
        if (cachedCoins.isEmpty()) {
            fetchCoins()
        }
        return coinDAO.getFilteredCoins("$filter%", offset, PAGE_SIZE)
    }

    private suspend fun fetchCoins(){
        val resource = dataSource.fetchCoins()
        if (resource.status == Resource.Status.SUCCESS){
            resource.data?.let {
                coinDAO.insertAll(resource.data)
            }
        }
    }
}