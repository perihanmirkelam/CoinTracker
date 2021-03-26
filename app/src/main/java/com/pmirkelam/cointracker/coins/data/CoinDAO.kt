package com.pmirkelam.cointracker.coins.data

import androidx.room.*


@Dao
interface CoinDAO {

    @Query("SELECT * FROM coins LIMIT :limit OFFSET :offset")
    suspend fun getPagedCoins(offset: Int, limit: Int): List<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Query("SELECT * FROM coins WHERE name LIKE :search OR symbol LIKE :search LIMIT :limit OFFSET :offset")
    suspend fun getFilteredCoins(search: String?, offset: Int, limit: Int): List<Coin>

}
