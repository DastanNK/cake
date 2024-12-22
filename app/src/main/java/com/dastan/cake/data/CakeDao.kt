package com.dastan.cake.data

import androidx.room.*
import com.dastan.cake.data.model.CakeOrder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CakeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addACake(cakeOrderEntity: CakeOrder)

    // Loads all wishes from the wish table
    @Query("Select * from `cake-table`")
    abstract fun getAllCakes(): Flow<List<CakeOrder>>

    @Update
    abstract suspend fun updateACake(cakeOrderEntity: CakeOrder)

    @Delete
    abstract suspend fun deleteACake(cakeOrderEntity: CakeOrder)

    @Query("Select * from `cake-table` where `cake-title` = :title AND `cake-price` =:price")
    abstract fun getACakeById(title:String, price:String): Flow<CakeOrder>


}