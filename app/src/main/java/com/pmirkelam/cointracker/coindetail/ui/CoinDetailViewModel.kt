package com.pmirkelam.cointracker.coindetail.ui

import androidx.lifecycle.*
import com.pmirkelam.cointracker.coindetail.data.CoinDetailRepository
import com.pmirkelam.cointracker.coindetail.data.CoinDetail
import com.pmirkelam.cointracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val repository: CoinDetailRepository) :
    ViewModel() {

    private val _id = MutableLiveData<String>()
    private val _isFavorite = MutableLiveData(false)

    private var _coinDetail = _id.switchMap { id ->
        viewModelScope.launch {
            fetchFavoriteStatus()
        }
        repository.getCoinDetail(id)
    }

    val coinDetail: LiveData<Resource<CoinDetail>> = _coinDetail
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun start(id: String) {
        _id.value = id
    }

    private fun getCoinDetail() {
        viewModelScope.launch {
            _coinDetail = repository.getCoinDetail(_id.value!!)
        }
    }

    private suspend fun fetchFavoriteStatus() {
        _isFavorite.value = repository.isFavorite(_id.value!!)
    }

    fun favoriteButtonClicked() {
        viewModelScope.launch {
            _id.value?.let { id ->

                if (!repository.isFavorite(id)) {
                    repository.setFavorite(_coinDetail.value?.data!!).await()

                } else {
                    repository.deleteFavorite(_coinDetail.value?.data!!).await()
                }

                fetchFavoriteStatus()
            }
        }
    }

    fun refresh() {
        getCoinDetail()
    }

    fun setRefreshInterval() {

    }
}