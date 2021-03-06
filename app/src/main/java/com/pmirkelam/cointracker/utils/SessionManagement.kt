package com.pmirkelam.cointracker.utils

import android.content.SharedPreferences
import com.pmirkelam.cointracker.auth.data.User
import javax.inject.Inject

class SessionManagement @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
    ) {

    companion object {
        const val KEY_IS_LOGIN: String = "is_login"
        const val KEY_USER_ID: String = "user_id"
        const val KEY_USER_NAME: String = "user_name"
        const val KEY_USER_EMAIL: String = "user_email"
    }

    fun createSession(status: Boolean, user: User) {
        editor.putBoolean(KEY_IS_LOGIN, status)
        editor.putString(KEY_USER_ID, user.id)
        editor.putString(KEY_USER_NAME, user.name)
        editor.putString(KEY_USER_EMAIL, user.email)
        editor.commit()
    }

    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun getUser(): User {
        return User(
            id = sharedPreferences.getString(KEY_USER_ID, ""),
            name = sharedPreferences.getString(KEY_USER_NAME, "") ?: "",
            email = sharedPreferences.getString(KEY_USER_EMAIL, "") ?: ""
        )
    }

    fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false)
    }
}