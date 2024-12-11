package com.dastan.cake.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dastan.cake.data.ItemState
import com.dastan.cake.data.RetrofitInstance
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InfoViewModel:ViewModel() {
    private val _itemState = mutableStateOf(ItemState())
    private val _retrieveItemState = mutableStateOf(ItemState())
    val retrieveItemState: State<ItemState> = _retrieveItemState
    private val queryInput = Channel<String>(Channel.CONFLATED)
    val queryText = MutableStateFlow("")

    init {
        fetchCategories()
        fetchCakeByName()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.retrieveData()
                //Log.d("ViewModel", response.cake?.getOrNull(0)?.id ?: "No ID")
                _itemState.value = _itemState.value.copy(
                    listCake = response.cake?: emptyList(),
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _itemState.value = _itemState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )

            }
            _retrieveItemState.value=_itemState.value
        }
    }

    fun onQueryUpdate(input: String) {
        queryText.value = input
        queryInput.trySend(input)
    }

    private fun fetchCakeByName() {
        viewModelScope.launch {
            queryInput.receiveAsFlow()
                .debounce(500)
                .collect { query -> onSearch(query) }
        }
    }

    private fun onSearch(query: String) {
        val allCakes = _itemState.value.listCake
        val filteredCakes = if (query.isNotEmpty()) {
            allCakes.filter { it.title?.contains(query, ignoreCase = true)==true }
        } else {
            allCakes
        }
        _retrieveItemState.value = _itemState.value.copy(listCake = filteredCakes)
    }

}