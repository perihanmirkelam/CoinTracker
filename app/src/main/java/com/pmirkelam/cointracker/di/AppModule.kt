package com.pmirkelam.cointracker.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pmirkelam.cointracker.coins.data.CoinRepository
import com.pmirkelam.cointracker.coins.data.CoinDAO
import com.pmirkelam.cointracker.AppDatabase
import com.pmirkelam.cointracker.coindetail.data.CoinDetailDAO
import com.pmirkelam.cointracker.api.CoinRemoteDataSource
import com.pmirkelam.cointracker.api.CoinService
import com.pmirkelam.cointracker.coindetail.data.CoinDetailRepository
import com.pmirkelam.cointracker.utils.Constants.API_BASE_URL
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
        .baseUrl(API_BASE_URL)
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
    fun provideCoinDetailDao(db: AppDatabase) = db.coinDetailDao()

    @Singleton
    @Provides
    fun provideCoinRepository(
        remoteDataSource: CoinRemoteDataSource,
        localDataSourceCoin: CoinDAO,
    ) =
        CoinRepository(remoteDataSource, localDataSourceCoin)

    @Singleton
    @Provides
    fun provideCoinDetailRepository(
        remoteDataSource: CoinRemoteDataSource,
        localDataSourceCoinDetail: CoinDetailDAO,
    ) =
        CoinDetailRepository(remoteDataSource, localDataSourceCoinDetail)

}