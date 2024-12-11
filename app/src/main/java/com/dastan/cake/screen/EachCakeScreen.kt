package com.dastan.cake.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dastan.cake.data.CakeInfo
import com.dastan.cake.data.CakeOrder
import com.dastan.cake.data.Screens
import com.dastan.cake.domain.FirebaseViewModel
import com.dastan.cake.domain.InfoViewModel
import com.dastan.cake.domain.OrderViewModel

@Composable
fun EachCakeScreen(firebaseViewModel: FirebaseViewModel,navController: NavController, cakeInfo:CakeInfo, orderViewModel: OrderViewModel){
    val imageUri by firebaseViewModel.imageUri.collectAsState()
    val newImageImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            firebaseViewModel.postImage(uri)
        }

    }

    val choose = remember { mutableStateOf(0) }
    val selectedWeight = when (choose.value) {
        0 -> cakeInfo.weight?.weightSmall?: "N/A"
        1 -> cakeInfo.weight?.weightMedium?: "N/A"
        2 -> cakeInfo.weight?.weightBig?: "N/A"
        else -> cakeInfo.weight?.weightSmall?: "N/A"
    }

    val selectedPrice = when (choose.value) {
        0 -> cakeInfo.price?.priceSmall?: "N/A"
        1 -> cakeInfo.price?.priceMedium?: "N/A"
        2 -> cakeInfo.price?.priceBig?: "N/A"
        else -> cakeInfo.price?.priceSmall?: "N/A"
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row{
            Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.clickable { navController.navigateUp() })
            Text(cakeInfo.title.toString())
        }
        Text(text = selectedWeight)
        choosingSize(choose)
        Row{
            Text(text = selectedPrice)
            Box(modifier = Modifier.clickable {
                orderViewModel.addCake(

                    CakeOrder(
                        //id= cakeInfo.id!!.toLong(),
                        title = cakeInfo.title?:"",
                        description = cakeInfo.description?:"",
                        price = selectedPrice,
                        weight = selectedWeight,
                        quantity = 1,
                        imageUri = imageUri?:""
                    )
                )
                navController.navigateUp()
            }){
                Row{
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Добавить")
                }

            }
        }
        Column {
            Text("Customize Your Cake", modifier = Modifier.clickable {
                navController.navigate(Screens.CustomScreen.route)
            })
            Text("Add photo to cake", modifier = Modifier.clickable {
                newImageImageLauncher.launch("image/*")
            })
        }



    }

}



@Composable
fun choosingSize(choose: MutableState<Int>){
    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
            .clip(shape = RoundedCornerShape(40))
            .background(color = colorScheme.onSurface)
    ) {
        val bigButtonColor by animateColorAsState(
            targetValue = if (choose.value==2) colorScheme.onTertiary else colorScheme.onSurface
        )
        val mediumButtonColor by animateColorAsState(
            targetValue = if (choose.value==1) colorScheme.onTertiary else colorScheme.onSurface
        )
        val smallButtonColor by animateColorAsState(
            targetValue = if (choose.value==0) colorScheme.onTertiary else colorScheme.onSurface
        )
        Row {
            Button(
                onClick = {
                    choose.value = 0
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = smallButtonColor
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Мини", color = colorScheme.onPrimary)
            }
            Button(
                onClick = {
                    choose.value = 1
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mediumButtonColor
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Средний", color = colorScheme.onPrimary)
            }
            Button(
                onClick = {
                    choose.value = 2
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = bigButtonColor
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Большой", color = colorScheme.onPrimary)
            }

        }


    }
}