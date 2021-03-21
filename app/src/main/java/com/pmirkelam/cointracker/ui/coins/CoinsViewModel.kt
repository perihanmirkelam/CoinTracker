package com.pmirkelam.cointracker.ui.coins

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.data.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CoinsViewModel @Inject constructor(repository: CoinRepository) : ViewModel() {
    val coins = repository.getCoins()
}
