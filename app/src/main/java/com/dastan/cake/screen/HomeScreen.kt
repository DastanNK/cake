package com.dastan.cake.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dastan.cake.FindField
import com.dastan.cake.data.model.CakeInfo
import com.dastan.cake.data.model.Screens
import com.dastan.cake.domain.FirebaseViewModel
import com.dastan.cake.domain.InfoViewModel
import com.dastan.cake.R

@Composable
fun HomeScreen(navController: NavController, viewModel: InfoViewModel, firebaseViewModel: FirebaseViewModel) {
    val result by viewModel.retrieveItemState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(end=20.dp), horizontalArrangement = Arrangement.SpaceBetween){
            FindField(viewModel)
            Icon(painter = painterResource(R.drawable.cart_3), contentDescription = null,modifier = Modifier.padding(4.dp).height(40.dp).width(40.dp)
                .clickable { navController.navigate(Screens.CartScreen.route) }
            )
            /*Box(modifier = Modifier
                //.padding(8.dp)
                //.clip(shape = RoundedCornerShape(10))
                //.background(color = MaterialTheme.colorScheme.background)
                .clickable {
                    navController.navigate(Screens.CartScreen.route)
                }){

                //Text("Корзина")
            }*/
        }

        when {
            result.loading -> {
                Text("Loading...")
            }

            result.error != null -> {
                Text(result.error.toString())
            }

            else -> {
                Items(result.listCake, navController, firebaseViewModel)
            }
        }
    }
}

@Composable
fun Items(results: List<CakeInfo>, navController: NavController, firebaseViewModel: FirebaseViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(results) { result ->
            EachItems(result, navController, firebaseViewModel)
        }
    }
}

@Composable
fun EachItems(result: CakeInfo, navController: NavController, firebaseViewModel: FirebaseViewModel) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(10))
            .background(color = MaterialTheme.colorScheme.background)
            .clickable {
                firebaseViewModel.setImageUri("")
                navController.navigate(Screens.EachCakeScreen.createRoute(result))
            }
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model=result.image, contentDescription = null, modifier = Modifier.width(160.dp).height(160.dp))
            }
            Text(result.title?:"Торт", fontSize = 14.sp)
            Row(verticalAlignment = Alignment.Bottom) {
                Text("от", fontSize = 12.sp, modifier = Modifier.padding(end = 2.dp))
                Text("${result.price?.priceSmall?:0} тг", fontSize = 16.sp)
            }
        }
    }
}