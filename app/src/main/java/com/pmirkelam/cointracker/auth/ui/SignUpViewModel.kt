package com.pmirkelam.cointracker.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pmirkelam.cointracker.auth.data.User
import com.pmirkelam.cointracker.utils.Constants.ENTER_EMAIL
import com.pmirkelam.cointracker.utils.Constants.ENTER_NAME
import com.pmirkelam.cointracker.utils.Constants.ENTER_PASSWORD
import com.pmirkelam.cointracker.utils.Constants.UNKNOWN_ERROR
import com.pmirkelam.cointracker.utils.SessionManagement
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val sessionManagement: SessionManagement,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    private val _isSignedUp = MutableLiveData(sessionManagement.isLoggedIn())
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
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    saveRemoteDb(user)

                } else {
                    _errorMessage.value = task.exception?.message ?: UNKNOWN_ERROR
                    _showProgress.value = false
                }
            }
    }

    private fun saveRemoteDb(user: User){
        // save user to firestore
        auth.uid?.let {
            val map: HashMap<String, String> = HashMap()
            user.id = it
            map["id"] = it
            map["name"] = user.name
            map["email"] = user.email
            map["password"] = user.password
            firestore.collection("users/")
                .document(it)
                .set(map as Map<String, Any>)
                .addOnCompleteListener { task ->

                    _showProgress.value = false
                    _isSignedUp.value = true

                    if (task.isSuccessful) {
                        sessionManagement.createSession(true, user)
                    } else {
                        _errorMessage.value = task.exception?.message ?: UNKNOWN_ERROR
                    }
                }
        }
    }

    private fun isCredentialsValid(user: User): Boolean {
        return when {
            user.name.isEmpty() -> {
                _errorMessage.value = ENTER_NAME
                false
            }
            user.email.isEmpty() -> {
                _errorMessage.value = ENTER_EMAIL
                false
            }
            user.password.isEmpty() -> {
                _errorMessage.value = ENTER_PASSWORD
                false
            }
            else -> true
        }
    }
}