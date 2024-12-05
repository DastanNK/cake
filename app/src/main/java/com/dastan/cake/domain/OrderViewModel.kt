package com.dastan.cake.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dastan.cake.Graph
import com.dastan.cake.data.CakeOrder
import com.dastan.cake.data.CakeOrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OrderViewModel(private val cakeOrderRepository: CakeOrderRepository=Graph.cakeOrderRepository):ViewModel() {
    fun getACakeById(id:Long): Flow<CakeOrder> {
        return cakeOrderRepository.getACakeById(id)
    }

    fun updateCake(cakeOrder: CakeOrder){
        viewModelScope.launch(Dispatchers.IO) {
            cakeOrderRepository.updateACake(cakeOrder= cakeOrder)
        }
    }

    fun addCake(cakeOrder: CakeOrder){
        viewModelScope.launch(Dispatchers.IO) {
            cakeOrderRepository.addACake(cakeOrder= cakeOrder)
        }
    }

    lateinit var getAllCakes: Flow<List<CakeOrder>>

    init {
        viewModelScope.launch {
            getAllCakes = cakeOrderRepository.getAllCakes()
        }
    }
}