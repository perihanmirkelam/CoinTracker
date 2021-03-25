package com.pmirkelam.cointracker.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmirkelam.cointracker.coindetail.data.CoinDetail
import com.pmirkelam.cointracker.favorites.data.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) :
    ViewModel() {

    private val _favorites = MutableLiveData<List<CoinDetail>>()
    val favorites: LiveData<List<CoinDetail>> = _favorites

    init {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }

}
