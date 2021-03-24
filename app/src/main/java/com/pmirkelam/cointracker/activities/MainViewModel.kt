package com.pmirkelam.cointracker.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmirkelam.cointracker.auth.data.User
import com.pmirkelam.cointracker.auth.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val _user = MutableLiveData(repository.user)

    val user: LiveData<User>
        get() =_user

    fun clearUser() = repository.clearUser()

}