package com.pmirkelam.cointracker.coins.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
)
