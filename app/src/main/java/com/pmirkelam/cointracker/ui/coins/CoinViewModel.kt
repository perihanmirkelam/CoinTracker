package com.pmirkelam.cointracker.ui.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.data.CoinRepository

class CoinViewModel : ViewModel() {

    private val _coins = MutableLiveData<List<Coin>>().apply {
        value = CoinRepository.coinsLiveData.value
    }

    val coins : LiveData<List<Coin>> = _coins
}