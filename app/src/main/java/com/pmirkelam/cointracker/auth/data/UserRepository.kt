package com.pmirkelam.cointracker.auth.data

import com.pmirkelam.cointracker.utils.SessionManagement
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val fireBaseSource: FirebaseSource,
    private val sessionManagement: SessionManagement
) {

    val user = sessionManagement.getUser()

    val uid = fireBaseSource.getUid()

    fun signUpUser(user: User) = fireBaseSource.signUpUser(user)

    fun signInUser(email: String, password: String) = fireBaseSource.signInUser(email, password)

    fun saveUser(user: User) = fireBaseSource.saveUser(user)

    fun fetchUser() = fireBaseSource.fetchUser()

    fun saveUserToPref(status: Boolean, user: User) = sessionManagement.createSession(status, user)

    fun isLoggedIn(): Boolean = sessionManagement.isLoggedIn()

    fun clearUser() = sessionManagement.clearUser()

}