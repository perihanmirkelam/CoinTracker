package com.pmirkelam.cointracker.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmirkelam.cointracker.auth.data.User
import com.pmirkelam.cointracker.auth.data.UserRepository
import com.pmirkelam.cointracker.utils.Constants.WARN_ENTER_EMAIL
import com.pmirkelam.cointracker.utils.Constants.WARN_ENTER_PASSWORD
import com.pmirkelam.cointracker.utils.Constants.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    private val _isLoggedIn = MutableLiveData(repository.isLoggedIn())
    private val _errorMessage = MutableLiveData<String>()
    private val _signUpNeeded = MutableLiveData<Boolean>()

    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val signUpNeeded: LiveData<Boolean>
        get() = _signUpNeeded

    fun navigated() {
        _signUpNeeded.value = false
    }

    fun onLoginButtonClicked() {
        val user = User(
            email = email.value.toString().trim(),
            password = password.value.toString().trim()
        )
        if (isCredentialsValid(user)) {
            loginUser(user)
        }
    }

    fun onCreateUserClicked() {
        _signUpNeeded.value = true
    }

    private fun loginUser(user: User) {
        _showProgress.value = true

        repository.signInUser(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _showProgress.value = false
                _isLoggedIn.value = true
                repository.saveUserToPref(true, user)
            } else {
                _errorMessage.value = task.exception?.message ?: UNKNOWN_ERROR
                _showProgress.value = false
            }
        }
    }

    private fun isCredentialsValid(user: User): Boolean {
        return when {
            user.email.isEmpty() -> {
                _errorMessage.value = WARN_ENTER_EMAIL
                return false
            }
            user.password.isEmpty() -> {
                _errorMessage.value = WARN_ENTER_PASSWORD
                return false
            }
            else -> true
        }
    }

}
