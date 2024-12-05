package com.dastan.cake

import android.content.Context
import androidx.room.Room
import com.dastan.cake.data.CakeOrderDatabase
import com.dastan.cake.data.CakeOrderRepository

object Graph {
    lateinit var database: CakeOrderDatabase

    val cakeOrderRepository by lazy{
        CakeOrderRepository(cakeDao = database.cakeDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, CakeOrderDatabase::class.java, "cake.db").build()
    }

}