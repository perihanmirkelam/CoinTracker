package com.pmirkelam.cointracker.coins.ui

import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pmirkelam.cointracker.coins.data.Coin
import com.pmirkelam.cointracker.coins.data.CoinRepository
import com.pmirkelam.cointracker.coins.data.CoinsPagingSource
import com.pmirkelam.cointracker.coins.data.FilteredCoinsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    val flow: Flow<PagingData<Coin>>
        get() = getCoins()

    val filteredFlow: Flow<PagingData<Coin>>
        get() = getFilteredCoins()

    private var _filter: String = ""

    private val _isFiltered = MutableLiveData(false)

    val isFiltered: LiveData<Boolean>
        get() = _isFiltered

    private fun getCoins(): Flow<PagingData<Coin>> {
        return Pager(PagingConfig(pageSize = 4, prefetchDistance = 4)) {
            CoinsPagingSource(repository)
        }.flow.cachedIn(viewModelScope)
    }

    private fun getFilteredCoins(): Flow<PagingData<Coin>> {
        return Pager(PagingConfig(pageSize = 4, prefetchDistance = 4)) {
            FilteredCoinsPagingSource(repository, _filter)
        }.flow.cachedIn(viewModelScope)
    }

    val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            _filter = newText
            // Do not use filtered query for one char
            // Make regular query when search text is removed
            _isFiltered.value = (newText.length > 1)
            return true
        }

        override fun onQueryTextSubmit(newQuery: String): Boolean {
            return true
        }
    }

}
