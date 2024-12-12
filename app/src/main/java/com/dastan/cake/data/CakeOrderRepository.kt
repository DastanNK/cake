package com.dastan.cake.data

import kotlinx.coroutines.flow.Flow

class CakeOrderRepository(private val cakeDao: CakeDao) {

    suspend fun addACake(cakeOrder:CakeOrder){
        cakeDao.addACake(cakeOrder)
    }

    fun getAllCakes(): Flow<List<CakeOrder>> = cakeDao.getAllCakes()

    fun getACakeById(title:String, price:String) : Flow<CakeOrder> {
        return cakeDao.getACakeById(title, price)
    }

    suspend fun updateACake(cakeOrder:CakeOrder){
        cakeDao.updateACake(cakeOrder)
    }

    suspend fun deleteACake(cakeOrder: CakeOrder){
        cakeDao.deleteACake(cakeOrder)
    }

}