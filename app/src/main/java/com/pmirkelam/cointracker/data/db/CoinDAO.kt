package com.pmirkelam.cointracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.data.CoinDetail

@Dao
interface CoinDAO {

    @Query("SELECT * FROM coins")
    fun getAllCoins(): LiveData<List<Coin>>

    @Query("SELECT * FROM coin_details WHERE id = :id")
    fun getCoin(id: String): LiveData<CoinDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinDetail)

}