package com.dastan.cake.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [CakeOrder::class],
    version = 1,
    exportSchema = false
)
abstract class CakeOrderDatabase: RoomDatabase() {
    abstract fun cakeDao(): CakeDao
}