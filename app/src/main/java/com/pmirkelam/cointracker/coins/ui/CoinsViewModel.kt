package com.pmirkelam.cointracker.coins.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.pmirkelam.cointracker.coins.data.CoinRepository
import com.pmirkelam.cointracker.coins.data.CoinsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(repository: CoinRepository) : ViewModel() {
    val flow = Pager(PagingConfig(pageSize = 4, prefetchDistance = 4)) {
        CoinsPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}
