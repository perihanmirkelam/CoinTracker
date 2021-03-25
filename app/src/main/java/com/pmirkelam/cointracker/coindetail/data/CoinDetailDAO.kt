package com.pmirkelam.cointracker.coindetail.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CoinDetailDAO {

    @Query("SELECT * FROM coin_details WHERE id = :id")
    fun getCoinDetail(id: String): LiveData<CoinDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinDetail)

    @Update
    suspend fun update(coin: CoinDetail)

}
