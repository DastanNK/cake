package com.dastan.cake.domain

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dastan.cake.Graph
import com.dastan.cake.bitmapToFile
import com.dastan.cake.data.model.CakeOrder
import com.dastan.cake.data.CakeOrderRepository
import com.dastan.cake.fileToUri
import com.dastan.cake.imageBitmapToBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderViewModel(private val cakeOrderRepository: CakeOrderRepository = Graph.cakeOrderRepository) : ViewModel() {
    private val _cakeOrder = MutableStateFlow<CakeOrder?>(null)
    val cakeOrder: StateFlow<CakeOrder?> = _cakeOrder

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice


    private fun getACakeById(title: String, price: String): Flow<CakeOrder?> {
        return cakeOrderRepository.getACakeById(title, price)
    }

    fun existenceOfItem(title: String, price: String) {
        viewModelScope.launch {
            getACakeById(title, price).collect { cake ->
                _cakeOrder.value = cake
            }
        }
    }



    fun addImageBitmap(imageBitmap: ImageBitmap, context: Context) {
        val bitmap = imageBitmapToBitmap(imageBitmap)

        val file = bitmapToFile(context, bitmap)

        val uri = fileToUri(context, file)
    }

    fun increaseQuantityCake(cakeOrder: CakeOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedCakeOrder = cakeOrder.copy(quantity = cakeOrder.quantity + 1)
            cakeOrderRepository.updateACake(updatedCakeOrder)
            getTotalPrice()
        }
    }

    fun decreaseQuantityCake(cakeOrder: CakeOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedCakeOrder = cakeOrder.copy(quantity = cakeOrder.quantity - 1)
            cakeOrderRepository.updateACake(updatedCakeOrder)
            getTotalPrice()
        }
    }

    fun addCake(cakeOrder: CakeOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            cakeOrderRepository.addACake(cakeOrder = cakeOrder)
            getTotalPrice()
        }
    }

    lateinit var getAllCakes: Flow<List<CakeOrder>>

    init {
        viewModelScope.launch {
            getAllCakes = cakeOrderRepository.getAllCakes()
            getTotalPrice()
        }
    }

    fun getEachPrice(cakeOrder: CakeOrder):Int{
        return cakeOrder.quantity * cakeOrder.price.toInt()
    }

    fun getTotalPrice(){
        viewModelScope.launch {
            _totalPrice.value = getAllCakes.first().sumOf { getEachPrice(it) }
        }
    }

    fun deleteCake(cakeOrder: CakeOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            cakeOrderRepository.deleteACake(cakeOrder = cakeOrder)
            getAllCakes = cakeOrderRepository.getAllCakes()
            getTotalPrice()
        }
    }
}