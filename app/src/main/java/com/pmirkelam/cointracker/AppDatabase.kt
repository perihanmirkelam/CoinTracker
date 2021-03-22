package com.pmirkelam.cointracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pmirkelam.cointracker.coindetail.data.CoinDetailDAO
import com.pmirkelam.cointracker.coins.data.CoinDAO
import com.pmirkelam.cointracker.coins.data.Coin
import com.pmirkelam.cointracker.data.CoinDetail

@Database(entities = [Coin::class, CoinDetail::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDAO
    abstract fun coinDetailDao(): CoinDetailDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "coins.db")
               // .fallbackToDestructiveMigration()
                .build()
    }
}
