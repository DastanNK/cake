package com.dastan.cake.domain

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dastan.cake.Graph
import com.dastan.cake.bitmapToFile
import com.dastan.cake.data.CakeOrder
import com.dastan.cake.data.CakeOrderRepository
import com.dastan.cake.fileToUri
import com.dastan.cake.imageBitmapToBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OrderViewModel(private val cakeOrderRepository: CakeOrderRepository=Graph.cakeOrderRepository):ViewModel() {
    fun getACakeById(id:Long): Flow<CakeOrder> {
        return cakeOrderRepository.getACakeById(id)
    }
    fun addImageBitmap(imageBitmap:ImageBitmap, context: Context){
        val bitmap = imageBitmapToBitmap(imageBitmap)

        val file = bitmapToFile(context, bitmap)

        val uri = fileToUri(context, file)
    }

    fun increaseQuantityCake(cakeOrder: CakeOrder){
        viewModelScope.launch(Dispatchers.IO) {
            val updatedCakeOrder = cakeOrder.copy(quantity = cakeOrder.quantity + 1)
            cakeOrderRepository.updateACake(updatedCakeOrder)
        }
    }
    fun decreaseQuantityCake(cakeOrder: CakeOrder){
        viewModelScope.launch(Dispatchers.IO) {
            val updatedCakeOrder = cakeOrder.copy(quantity = cakeOrder.quantity - 1)
            cakeOrderRepository.updateACake(updatedCakeOrder)
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
    fun deleteCake(cakeOrder: CakeOrder){
        viewModelScope.launch(Dispatchers.IO) {
            cakeOrderRepository.deleteACake(cakeOrder= cakeOrder)
            getAllCakes = cakeOrderRepository.getAllCakes()
        }
    }
}