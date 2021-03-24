package com.pmirkelam.cointracker.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pmirkelam.cointracker.api.CoinRemoteDataSource
import com.pmirkelam.cointracker.api.CoinService
import com.pmirkelam.cointracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideApiRemoteDataSource(coinService: CoinService) =
        CoinRemoteDataSource(coinService)

    @Provides
    fun provideApiService(retrofit: Retrofit): CoinService =
        retrofit.create(CoinService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGSON(): Gson = GsonBuilder().create()
}