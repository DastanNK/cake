package com.dastan.cake.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dastan.cake.FindField
import com.dastan.cake.data.CakeInfo
import com.dastan.cake.data.Screens
import com.dastan.cake.domain.InfoViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: InfoViewModel) {
    val result by viewModel.retrieveItemState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        FindField(viewModel)
        when {
            result.loading -> {
                Text("Loading...")
            }

            result.error != null -> {
                Text(result.error.toString())
            }

            else -> {
                Items(result.listCake, navController)
            }
        }
    }
}

@Composable
fun Items(results: List<CakeInfo>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(results) { result ->
            EachItems(result, navController)
        }
    }
}

@Composable
fun EachItems(result: CakeInfo, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(10))
            .background(color = MaterialTheme.colorScheme.background)
            .clickable {
                navController.navigate(Screens.EachCakeScreen.createRoute(result))
            }
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Text(result.title?:"Торт", fontSize = 14.sp)
            Text(result.description?:"Торт", fontSize = 12.sp)
            Row(verticalAlignment = Alignment.Bottom) {
                Text("от", fontSize = 12.sp, modifier = Modifier.padding(end = 2.dp))
                Text("${result.price?.priceSmall?:0} тг", fontSize = 16.sp)
            }
        }
    }
}