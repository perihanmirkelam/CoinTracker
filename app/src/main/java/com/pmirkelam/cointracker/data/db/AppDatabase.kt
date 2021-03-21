package com.pmirkelam.cointracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.data.CoinDetail
import com.pmirkelam.cointracker.utils.Converters

@Database(entities = [Coin::class, CoinDetail::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDAO

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