package com.pmirkelam.cointracker.coindetail.ui

import androidx.lifecycle.*
import com.pmirkelam.cointracker.coindetail.data.CoinDetailRepository
import com.pmirkelam.cointracker.data.CoinDetail
import com.pmirkelam.cointracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(repository: CoinDetailRepository) : ViewModel() {
    private val _id = MutableLiveData<String>()

    private val _coin = _id.switchMap { id ->
        repository.getCoin(id)
    }
    val coin: LiveData<Resource<CoinDetail>> = _coin

    fun start(id: String) {
        _id.value = id
    }
}
