package com.pmirkelam.cointracker.coindetail.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pmirkelam.cointracker.data.CoinDetail

@Dao
interface CoinDetailDAO {

    @Query("SELECT * FROM coin_details WHERE id = :id")
    fun getCoin(id: String): LiveData<CoinDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinDetail)

}