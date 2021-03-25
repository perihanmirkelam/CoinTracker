package com.pmirkelam.cointracker.favorites.data

import android.util.Log
import com.pmirkelam.cointracker.firebase.FirebaseSource
import com.pmirkelam.cointracker.coindetail.data.CoinDetail
import com.pmirkelam.cointracker.coindetail.data.CoinDetail.Companion.toCoinDetail
import com.pmirkelam.cointracker.utils.SessionManagement
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val sessionManagement: SessionManagement,
    private val firebaseSource: FirebaseSource,
) {

    suspend fun getFavorites(): List<CoinDetail> {
        val user = sessionManagement.getUser()
        return try {
            firebaseSource.getFavoriteCoinDetails(user)
                .await()
                .documents.mapNotNull {
                    it.toCoinDetail()
                }
        } catch (e: Exception) {
            Log.e(FavoritesRepository::class.simpleName, "Error getting favorites", e)
            emptyList()
        }
    }

}