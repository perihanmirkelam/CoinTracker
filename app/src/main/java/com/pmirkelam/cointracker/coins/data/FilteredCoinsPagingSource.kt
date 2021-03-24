package com.pmirkelam.cointracker.coins.data

import androidx.paging.PagingSource
import java.net.UnknownHostException

class FilteredCoinsPagingSource(
    private val coinRepository: CoinRepository,
    private val filter: String,
) : PagingSource<Int, Coin>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        return try {
            val nextPageNumber  = params.key ?: 0
            val coinPage = coinRepository.getFilteredPagedCoins(filter, nextPageNumber)
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