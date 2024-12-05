package com.dastan.cake.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CakeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addACake(wishEntity: Wish)

    // Loads all wishes from the wish table
    @Query("Select * from `wish-table`")
    abstract fun getAllCakes(): Flow<List<Wish>>

    @Update
    abstract suspend fun updateACake(wishEntity: Wish)

    @Delete
    abstract suspend fun deleteACake(wishEntity: Wish)

    @Query("Select * from `wish-table` where id=:id")
    abstract fun getACakeById(id:Long): Flow<Wish>


}