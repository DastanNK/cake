package com.dastan.cake.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.dastan.cake.domain.OrderViewModel

@Composable
fun CartScreen(navController: NavController, orderViewModel: OrderViewModel){
    val allCakes=orderViewModel.getAllCakes.collectAsState(initial = emptyList()).value
    LazyColumn {
        items(allCakes){cake->
            Text(cake.title)
        }
    }
}