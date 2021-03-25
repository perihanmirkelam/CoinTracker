package com.pmirkelam.cointracker.coins.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
) : Parcelable

