package com.dastan.cake.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dastan.cake.data.model.CakeOrder


@Database(
    entities = [CakeOrder::class],
    version = 2,
    exportSchema = false
)
abstract class CakeOrderDatabase: RoomDatabase() {
    abstract fun cakeDao(): CakeDao
}