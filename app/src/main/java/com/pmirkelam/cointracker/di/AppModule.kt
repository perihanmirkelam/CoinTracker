package com.pmirkelam.cointracker.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pmirkelam.cointracker.data.CoinRepository
import com.pmirkelam.cointracker.data.db.CoinDAO
import com.pmirkelam.cointracker.data.db.AppDatabase
import com.pmirkelam.cointracker.data.network.CoinRemoteDataSource
import com.pmirkelam.cointracker.data.network.CoinService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCoinService(retrofit: Retrofit): CoinService =
        retrofit.create(CoinService::class.java)

    @Singleton
    @Provides
    fun provideCoinRemoteDataSource(coinService: CoinService) = CoinRemoteDataSource(coinService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCoinDao(db: AppDatabase) = db.coinDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: CoinRemoteDataSource,
        localDataSource: CoinDAO
    ) =
        CoinRepository(remoteDataSource, localDataSource)

}