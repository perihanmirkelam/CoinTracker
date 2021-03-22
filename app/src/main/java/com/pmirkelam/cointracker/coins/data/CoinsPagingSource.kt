package com.pmirkelam.cointracker.coins.data

import androidx.paging.PagingSource
import java.net.UnknownHostException

class CoinsPagingSource(
    private val coinRepository: CoinRepository
) : PagingSource<Int, Coin>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        return try {
            val nextPageNumber  = params.key ?: 0
            val coinPage = coinRepository.getPagedCoins(nextPageNumber)
            LoadResult.Page(
                data = coinPage,
                prevKey = null,
                nextKey = nextPageNumber + 4
            )
        } catch (e: UnknownHostException) {
            // Unable to connect to the network
            LoadResult.Error(e)
        }
    }
}