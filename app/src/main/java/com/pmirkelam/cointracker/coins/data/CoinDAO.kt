package com.pmirkelam.cointracker.coins.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDAO {

    @Query("SELECT * FROM coins")
    fun getAllCoins(): LiveData<List<Coin>>

    @Query("SELECT * FROM coins LIMIT 4 OFFSET :offset")
    suspend fun get4Coins(offset: Int): List<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Query("SELECT * FROM coins WHERE name LIKE :search OR symbol LIKE :search LIMIT 4 OFFSET :offset")
    suspend fun getFilteredCoins(search: String?, offset: Int): List<Coin>

}