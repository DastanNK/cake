package com.dastan.cake.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="cake-table")
data class CakeOrder(@PrimaryKey(autoGenerate = true)
                     val id: Long = 0L,
                     @ColumnInfo(name="cake-title")
                     val title: String="",
                     @ColumnInfo(name="cake-desc")
                     val description:String="",
                     @ColumnInfo(name="cake-price")
                     val price:String="",
                     @ColumnInfo(name="cake-weight")
                     val weight:String="",
                     @ColumnInfo(name="cake-quantity")
                     val quantity:Int=0,
    )