package com.pmirkelam.cointracker.di

import com.pmirkelam.cointracker.api.CoinDataSource
import com.pmirkelam.cointracker.firebase.FirebaseSource
import com.pmirkelam.cointracker.auth.data.UserRepository
import com.pmirkelam.cointracker.coindetail.data.CoinDetailDAO
import com.pmirkelam.cointracker.coindetail.data.CoinDetailRepository
import com.pmirkelam.cointracker.coins.data.CoinDAO
import com.pmirkelam.cointracker.coins.data.CoinRepository
import com.pmirkelam.cointracker.favorites.data.FavoritesRepository
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
        dataSource: CoinDataSource,
        localDataSourceCoin: CoinDAO,
    ) = CoinRepository(dataSource, localDataSourceCoin)

    @Singleton
    @Provides
    fun provideCoinDetailRepository(
        dataSource: CoinDataSource,
        localDataSourceCoinDetail: CoinDetailDAO,
        fireBaseSource: FirebaseSource,
        sessionManagement: SessionManagement
    ) = CoinDetailRepository(
        dataSource,
        localDataSourceCoinDetail,
        fireBaseSource,
        sessionManagement,
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        fireBaseSource: FirebaseSource,
        sessionManagement: SessionManagement
    ) = UserRepository(fireBaseSource, sessionManagement)

    @Singleton
    @Provides
    fun provideFavoritesRepository(
        fireBaseSource: FirebaseSource,
        sessionManagement: SessionManagement
    ) = FavoritesRepository(sessionManagement, fireBaseSource)

}