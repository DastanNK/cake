package com.dastan.cake.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dastan.cake.R
import com.dastan.cake.data.model.CakeInfo
import com.dastan.cake.data.model.CakeOrder
import com.dastan.cake.data.model.Screens
import com.dastan.cake.data.model.Weight
import com.dastan.cake.domain.FirebaseViewModel
import com.dastan.cake.domain.OrderViewModel

@Composable
fun EachCakeScreen(
    firebaseViewModel: FirebaseViewModel,
    navController: NavController,
    cakeInfo: CakeInfo,
    orderViewModel: OrderViewModel
) {
    val imageUri by firebaseViewModel.imageUri.collectAsState()
    val context = LocalContext.current

    val isCustomizable = remember { mutableStateOf(false) }
    val choose = remember { mutableStateOf(0) }
    val selectedWeight = when (choose.value) {
        0 -> cakeInfo.weight?.weightSmall ?: "N/A"
        1 -> cakeInfo.weight?.weightMedium ?: "N/A"
        2 -> cakeInfo.weight?.weightBig ?: "N/A"
        else -> cakeInfo.weight?.weightSmall ?: "N/A"
    }
    val selectedPrice = when (choose.value) {
        0 -> cakeInfo.price?.priceSmall ?: "N/A"
        1 -> cakeInfo.price?.priceMedium ?: "N/A"
        2 -> cakeInfo.price?.priceBig ?: "N/A"
        else -> cakeInfo.price?.priceSmall ?: "N/A"
    }
    val cakeTitle = cakeInfo.title ?: ""
    val cakePrice = selectedPrice

    LaunchedEffect(cakeTitle, cakePrice) {
        orderViewModel.existenceOfItem(cakeTitle, cakePrice)
    }

    val existId = remember {
        mutableStateOf(false)
    }
    val existenceCakeOrder by orderViewModel.cakeOrder.collectAsState()

    LaunchedEffect(existenceCakeOrder) {
        existId.value = existenceCakeOrder != null
    }



    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.clickable { navController.navigateUp() })
        }
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Column (modifier = Modifier.weight(1f)){
                AsyncImage(
                    model = cakeInfo.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
                Text(
                    cakeInfo.title.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    cakeInfo.description.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 12.dp),
                    lineHeight = 16.sp
                )
                choosingSize(choose, cakeInfo.weight ?: Weight("N/A", "N/A", "N/A"))

                isConfiguration(isCustomizable)
                if (isCustomizable.value) {
                    configuration(firebaseViewModel, navController)

                }
            }
            Column {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Price", fontSize = 14.sp)
                        Text(text = selectedPrice, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Box(modifier = Modifier.clickable {
                        if (firebaseViewModel.isProgress.value) {
                            Toast.makeText(context, "Please wait a second to retrive your image", Toast.LENGTH_SHORT).show()
                        } else {
                            if (existId.value) {
                                orderViewModel.increaseQuantityCake(existenceCakeOrder!!)//тут будет старое изображение для второго cake не будет нового рисунка
                            } else {
                                orderViewModel.addCake(
                                    CakeOrder(
                                        //id= cakeInfo.id!!.toLong(),
                                        title = cakeInfo.title ?: "",
                                        description = cakeInfo.description ?: "",
                                        price = selectedPrice,
                                        weight = selectedWeight,
                                        quantity = 1,
                                        imageUri = imageUri ?: "",
                                        image = cakeInfo.image
                                    )
                                )
                            }
                            navController.navigateUp()
                        }

                    }) {
                        Box(
                            modifier = Modifier.background(
                                color = colorScheme.background,
                                shape = RoundedCornerShape(16.dp)
                            ).border(
                                width = 1.dp,
                                color = colorScheme.onBackground,
                                shape = RoundedCornerShape(16.dp)
                            ).height(40.dp).width(180.dp), contentAlignment = Alignment.Center
                        ) {
                            Row {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Text("Добавить", fontSize = 16.sp)
                            }

                        }
                    }
                }
            }

        }


    }

}

@Composable
fun isConfiguration(isCustomizable: MutableState<Boolean>) {
    Box(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).height(48.dp).fillMaxWidth()
            .clip(shape = RoundedCornerShape(20))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Text("Add customization")
                Text("Personalize your cake design")
            }
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(40.dp)
                    .clip(shape = RoundedCornerShape(50))
                    .background(
                        color = if (isCustomizable.value) colorScheme.onTertiary else Color.Gray,
                        shape = RoundedCornerShape(50)
                    )
                    .clickable { isCustomizable.value = !isCustomizable.value }
            ) {
                // Circle indicator that moves when the state changes
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(
                            if (isCustomizable.value) Alignment.CenterEnd else Alignment.CenterStart
                        )
                        .padding(5.dp) // Adds some padding inside the circle
                )
            }
        }

    }
}

@Composable
fun configuration(firebaseViewModel: FirebaseViewModel, navController: NavController) {
    val newImageImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            firebaseViewModel.postImage(uri)
        }
    }
    Column {
        Row(modifier = Modifier.clickable {
            navController.navigate(Screens.CustomScreen.route)
        }, verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(R.drawable.edit_01) , contentDescription = null,
                modifier = Modifier.height(20.dp).width(20.dp).padding(end = 4.dp), colorFilter = ColorFilter.tint(color = colorScheme.onBackground))
            Text("Draw an image on your cake")
        }
        Row (modifier = Modifier.clickable {
            newImageImageLauncher.launch("image/*")
        }, verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(R.drawable.camera_01), contentDescription = null,
                modifier = Modifier.height(20.dp).width(20.dp).padding(end = 4.dp), colorFilter = ColorFilter.tint(color = colorScheme.onBackground))
            Text("Upload a photo on your cake")
        }
    }
}


@Composable
fun choosingSize(choose: MutableState<Int>, weight: Weight) {

    val bigButtonColor by animateColorAsState(
        targetValue = if (choose.value == 2) colorScheme.onTertiary else colorScheme.background
    )
    val mediumButtonColor by animateColorAsState(
        targetValue = if (choose.value == 1) colorScheme.onTertiary else colorScheme.background
    )
    val smallButtonColor by animateColorAsState(
        targetValue = if (choose.value == 0) colorScheme.onTertiary else colorScheme.background
    )
    Row(modifier = Modifier.fillMaxWidth().padding(top=8.dp)) {
        Box(
            modifier = Modifier.padding(8.dp).clickable { choose.value = 0 }
                .background(color = smallButtonColor, shape = RoundedCornerShape(16.dp)).weight(1f).height(48.dp)
                .width(80.dp).clip(shape = RoundedCornerShape(20)).border(
                width = 1.dp,
                color = colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "S",
                    color = if (choose.value == 0) colorScheme.background else colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    weight.weightSmall.toString(),
                    color = if (choose.value == 0) colorScheme.background else colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }
        Box(
            modifier = Modifier.padding(8.dp).clickable { choose.value = 1 }
                .background(color = mediumButtonColor, shape = RoundedCornerShape(16.dp)).weight(1f).height(48.dp)
                .width(80.dp).clip(shape = RoundedCornerShape(20)).border(
                width = 1.dp,
                color = colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "M",
                    color = if (choose.value == 1) colorScheme.background else colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    weight.weightMedium.toString(),
                    color = if (choose.value == 1) colorScheme.background else colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }
        Box(
            modifier = Modifier.padding(8.dp).clickable { choose.value = 2 }
                .background(color = bigButtonColor, shape = RoundedCornerShape(16.dp)).weight(1f).height(48.dp)
                .width(80.dp).clip(shape = RoundedCornerShape(20)).border(
                width = 1.dp,
                color = colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "L",
                    color = if (choose.value == 2) colorScheme.background else colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    weight.weightBig.toString(),
                    color = if (choose.value == 2) colorScheme.background else colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}