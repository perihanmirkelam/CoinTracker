package com.pmirkelam.cointracker.di

import com.pmirkelam.cointracker.api.CoinRemoteDataSource
import com.pmirkelam.cointracker.auth.data.FirebaseSource
import com.pmirkelam.cointracker.auth.data.UserRepository
import com.pmirkelam.cointracker.coindetail.data.CoinDetailDAO
import com.pmirkelam.cointracker.coindetail.data.CoinDetailRepository
import com.pmirkelam.cointracker.coins.data.CoinDAO
import com.pmirkelam.cointracker.coins.data.CoinRepository
import com.pmirkelam.cointracker.utils.SessionManagement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoinRepository(
        remoteDataSource: CoinRemoteDataSource,
        localDataSourceCoin: CoinDAO,
    ) = CoinRepository(remoteDataSource, localDataSourceCoin)

    @Singleton
    @Provides
    fun provideCoinDetailRepository(
        remoteDataSource: CoinRemoteDataSource,
        localDataSourceCoinDetail: CoinDetailDAO,
    ) = CoinDetailRepository(remoteDataSource, localDataSourceCoinDetail)

    @Singleton
    @Provides
    fun provideUserRepository(
        fireBaseSource: FirebaseSource,
        sessionManagement: SessionManagement
    ) = UserRepository(fireBaseSource, sessionManagement)

}