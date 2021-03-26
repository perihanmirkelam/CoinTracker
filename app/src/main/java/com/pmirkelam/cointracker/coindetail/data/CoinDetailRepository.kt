package com.pmirkelam.cointracker.coindetail.data

import android.util.Log
import com.google.android.gms.tasks.Task
import com.pmirkelam.cointracker.api.CoinDataSource
import com.pmirkelam.cointracker.firebase.FirebaseSource
import com.pmirkelam.cointracker.coindetail.data.CoinDetail.Companion.toCoinDetail
import com.pmirkelam.cointracker.utils.SessionManagement
import com.pmirkelam.cointracker.utils.resultLiveData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(
    private val dataSource: CoinDataSource,
    private val coinDetailDAO: CoinDetailDAO,
    private val firebaseSource: FirebaseSource,
    private val sessionManagement: SessionManagement
) {

    fun getCoinDetail(id: String) = resultLiveData(
        databaseQuery = { coinDetailDAO.getCoinDetail(id) },
        networkCall = { dataSource.fetchCoin(id) },
        saveCallResult = { coinDetailDAO.insert(it) }
    )

    fun setFavorite(coinDetail: CoinDetail): Task<Void> {
        return firebaseSource.setFavoriteCoinDetail(sessionManagement.getUser(), coinDetail)
    }

    fun deleteFavorite(coinDetailId: String): Task<Void> {
        return firebaseSource.deleteFavoriteCoinDetail(sessionManagement.getUser(), coinDetailId)
    }

    suspend fun isFavorite(coinDetailId: String): Boolean {
        val user = sessionManagement.getUser()
        return try {
            firebaseSource.isFavoriteCoinDetail(user, coinDetailId)
                .await()
                .toCoinDetail() != null
        } catch (e: Exception) {
            Log.e(CoinDetailRepository::class.simpleName, "Error getting user favorites", e)
            false
        }
    }

}
