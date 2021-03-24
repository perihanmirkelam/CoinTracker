package com.pmirkelam.cointracker.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pmirkelam.cointracker.utils.Constants
import com.pmirkelam.cointracker.utils.SessionManagement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FireBaseModule {

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideSessionManagement(preferences: SharedPreferences, editor: SharedPreferences.Editor) =
        SessionManagement(preferences, editor)

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPrefEditor(preferences: SharedPreferences): SharedPreferences.Editor =
        preferences.edit()
}