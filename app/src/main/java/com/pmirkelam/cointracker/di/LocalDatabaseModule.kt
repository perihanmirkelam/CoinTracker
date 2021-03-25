package com.pmirkelam.cointracker.di

import android.content.Context
import com.pmirkelam.cointracker.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCoinDao(db: AppDatabase) = db.coinDao()

    @Singleton
    @Provides
    fun provideCoinDetailDao(db: AppDatabase) = db.coinDetailDao()

}
