package com.pmirkelam.cointracker.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmirkelam.cointracker.auth.data.User
import com.pmirkelam.cointracker.auth.data.UserRepository
import com.pmirkelam.cointracker.utils.Constants.WARN_ENTER_EMAIL
import com.pmirkelam.cointracker.utils.Constants.WARN_ENTER_MIN_LENGTH_PASSWORD
import com.pmirkelam.cointracker.utils.Constants.WARN_ENTER_NAME
import com.pmirkelam.cointracker.utils.Constants.WARN_ENTER_PASSWORD
import com.pmirkelam.cointracker.utils.Constants.PASSWORD_MIN_LENGTH
import com.pmirkelam.cointracker.utils.Constants.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    private val _isSignedUp = MutableLiveData(repository.isLoggedIn())
    private val _errorMessage = MutableLiveData<String>()
    private val _backToLogin = MutableLiveData<Boolean>()

    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    val isSignedUp: LiveData<Boolean>
        get() = _isSignedUp

    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val backToLogin: LiveData<Boolean>
        get() = _backToLogin

    fun navigated() {
        _isSignedUp.value = false
        _backToLogin.value = false
    }

    fun onBackToLoginClicked() {
        _backToLogin.value = true
    }

    fun onSignUpButtonClicked() {
        val user = User(
            email = email.value.toString().trim(),
            name = name.value.toString().trim(),
            password = password.value.toString().trim(),
        )

        if (isCredentialsValid(user)) {
            signUp(user)
        }
    }

    private fun signUp(user: User) {
        _showProgress.value = true
        repository
            .signUpUser(user)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    saveRemoteDb(user)

                } else {
                    _errorMessage.value = task.exception?.message ?: UNKNOWN_ERROR
                    _showProgress.value = false
                }
            }
    }

    private fun saveRemoteDb(user: User) {
        // save user to firestore
        repository.uid?.let {
            val map: HashMap<String, String> = HashMap()
            user.id = it
            map["id"] = it
            map["name"] = user.name
            map["email"] = user.email
            map["password"] = user.password

            repository
                .saveUser(user)
                .addOnCompleteListener { task ->

                    _showProgress.value = false
                    _isSignedUp.value = true

                    if (task.isSuccessful) {
                        repository.saveUserToPref(true, user)
                    } else {
                        _errorMessage.value = task.exception?.message ?: UNKNOWN_ERROR
                    }
                }
        }
    }

    private fun isCredentialsValid(user: User): Boolean {
        return when {
            user.name.isEmpty() -> {
                _errorMessage.value = WARN_ENTER_NAME
                false
            }
            user.email.isEmpty() -> {
                _errorMessage.value = WARN_ENTER_EMAIL
                false
            }
            user.password.isEmpty() -> {
                _errorMessage.value = WARN_ENTER_PASSWORD
                false
            }
            user.password.length < PASSWORD_MIN_LENGTH -> {
                _errorMessage.value = WARN_ENTER_MIN_LENGTH_PASSWORD
                false
            }
            else -> true
        }
    }
}
