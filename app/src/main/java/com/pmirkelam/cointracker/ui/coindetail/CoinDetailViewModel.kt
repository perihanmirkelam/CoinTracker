package com.pmirkelam.cointracker.ui.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.pmirkelam.cointracker.data.CoinDetail
import com.pmirkelam.cointracker.data.CoinRepository
import com.pmirkelam.cointracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(repository: CoinRepository) : ViewModel() {
    private val _id = MutableLiveData<String>()

    private val _coin = _id.switchMap { id ->
        repository.getCoin(id)
    }
    val coin: LiveData<Resource<CoinDetail>> = _coin

    fun start(id: String) {
        _id.value = id
    }
}
