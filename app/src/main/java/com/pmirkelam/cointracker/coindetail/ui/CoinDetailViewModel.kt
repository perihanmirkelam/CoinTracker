package com.pmirkelam.cointracker.coindetail.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
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

    private var _id: String? = null
    private val _refresh = MutableLiveData<String>()
    private val _isFavorite = MutableLiveData(false)
    private val _isInternalDialogActive = MutableLiveData(false)

    var interval = MutableLiveData<String>() //Bindable

    private var _handler: Handler? = null
    private val _runnable: Runnable = object : Runnable {
        override fun run() {
            refresh()
            _handler?.postDelayed(this, 10000)
        }
    }

    private var _coinDetail = _refresh.switchMap { id ->
        viewModelScope.launch {
            fetchFavoriteStatus()
        }
        repository.getCoinDetail(id)
    }

    val coinDetail: LiveData<Resource<CoinDetail>>
        get() = _coinDetail
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite
    val isInternalDialogActive: LiveData<Boolean>
        get() = _isInternalDialogActive

    fun start(id: String) {
        _id = id
        _refresh.value = id
    }

    fun refresh() {
        viewModelScope.launch {
            _id?.let { _refresh.value = it }
        }
    }

    private suspend fun fetchFavoriteStatus() {
        _id?.let { _isFavorite.value = repository.isFavorite(it) }
    }

    fun favoriteButtonClicked() {
        viewModelScope.launch {
            _coinDetail.value?.data?.let { coinDetail ->
                if (!repository.isFavorite(coinDetail.id)) repository.setFavorite(coinDetail).await()
                else repository.deleteFavorite(coinDetail.id).await()
            }
            fetchFavoriteStatus()
        }
    }

    fun setIntervalSelected() {
        interval.value?.let {
            _handler?.let { it.removeCallbacks(_runnable) }
            _handler = Handler(Looper.getMainLooper())
            _handler?.postDelayed(_runnable, interval.value?.toLong()!!)
        }
        closeDialog()
    }

    fun resetIntervalSelected() {
        _handler?.let { it.removeCallbacks(_runnable) }
        closeDialog()
    }

    fun cancelIntervalSelected() = closeDialog()


    private fun closeDialog() {
        _isInternalDialogActive.value = false
    }

    fun onDestroy() {
        _handler?.removeCallbacks(_runnable)
    }
}
