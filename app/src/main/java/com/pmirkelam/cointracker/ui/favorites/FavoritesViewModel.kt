package com.pmirkelam.cointracker.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmirkelam.cointracker.data.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(repository: CoinRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favorites Fragment"
    }
    val text: LiveData<String> = _text
}
