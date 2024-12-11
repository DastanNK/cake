package com.dastan.cake.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dastan.cake.data.CakeOrder
import com.dastan.cake.domain.OrderViewModel

@Composable
fun CartScreen(navController: NavController, orderViewModel: OrderViewModel){
    val allCakes=orderViewModel.getAllCakes.collectAsState(initial = emptyList()).value
    LazyColumn {
        items(allCakes){cake->
            EachCakeInCart(cake, orderViewModel)
        }
    }
}

@Composable
fun EachCakeInCart(cake:CakeOrder, orderViewModel: OrderViewModel){
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)){
        Column(modifier = Modifier.weight(3f)) {
            Text(cake.title)
            Text(cake.description, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier.height(24.dp).width(24.dp)
                    .clip(shape = RoundedCornerShape(40))
                    .background(color = colorScheme.onPrimary).clickable {
                        if(cake.quantity==1){
                            orderViewModel.deleteCake(cake)
                        }else{
                            orderViewModel.decreaseQuantityCake(cake)
                        }
                    }
            ){
                Text("-")
            }
            Text(cake.quantity.toString(), modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.height(24.dp).width(24.dp)
                    .clip(shape = RoundedCornerShape(40))
                    .background(color = colorScheme.onPrimary).clickable {
                        orderViewModel.increaseQuantityCake(cake)
                    }
            ){
                Text("+")
            }
        }

        Text(cake.price, modifier = Modifier.weight(1f))
    }

}