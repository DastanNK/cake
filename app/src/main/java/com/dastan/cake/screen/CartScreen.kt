package com.dastan.cake.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dastan.cake.data.model.CakeOrder
import com.dastan.cake.data.model.Screens
import com.dastan.cake.domain.OrderViewModel

@Composable
fun CartScreen(navController: NavController, orderViewModel: OrderViewModel) {
    val allCakes = orderViewModel.getAllCakes.collectAsState(initial = emptyList()).value
    val total by orderViewModel.totalPrice.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp, bottom = 8.dp)){
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.clickable { navController.navigateUp() })
            Text("My Cart", fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
        LazyColumn(modifier = Modifier.weight(1f).padding(start = 16.dp, end = 16.dp)) {
            items(allCakes) { cake ->
                EachCakeInCart(cake, orderViewModel)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp).padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total")
            Text(total.toString())
        }
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth().height(48.dp)
                .clip(shape = RoundedCornerShape(40))
                .background(color = colorScheme.onTertiary).clickable {
                    navController.navigate(Screens.CheckoutScreen.route)
                }.border(
                    width = 1.dp,
                    color = colorScheme.background,
                    shape = RoundedCornerShape(40)
                ), contentAlignment = Alignment.Center
        ) {
            Text("Proceed to Checkout", color = colorScheme.background)
        }
    }

}

@Composable
fun EachCakeInCart(cake: CakeOrder, orderViewModel: OrderViewModel) {
    val price = cake.price.toInt() * cake.quantity

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        AsyncImage(model = cake.image, contentDescription = null, modifier = Modifier.width(120.dp).height(120.dp))

        Column (modifier = Modifier.padding(start = 16.dp)) {
            Text(cake.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                "Size: ${cake.weight}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(price.toString(), modifier = Modifier.padding(bottom = 4.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Box(
                    modifier = Modifier.padding(end=8.dp).height(24.dp).width(24.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(color = colorScheme.background).clickable {
                            if (cake.quantity == 1) {
                                orderViewModel.deleteCake(cake)
                            } else {
                                orderViewModel.decreaseQuantityCake(cake)
                            }
                        }.border(
                            width = 1.dp,
                            color = colorScheme.onBackground,
                            shape = RoundedCornerShape(6.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text("-", color = colorScheme.onBackground)
                }
                Text(cake.quantity.toString(), modifier = Modifier.padding(end=8.dp))
                Box(
                    modifier = Modifier.height(24.dp).width(24.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(color = colorScheme.background).clickable {
                            orderViewModel.increaseQuantityCake(cake)
                        }.border(
                            width = 1.dp,
                            color = colorScheme.onBackground,
                            shape = RoundedCornerShape(6.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text("+", color = colorScheme.onBackground)
                }
            }
        }


    }

}