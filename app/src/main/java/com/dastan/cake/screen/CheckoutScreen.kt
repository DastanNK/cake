package com.dastan.cake.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dastan.cake.R
import com.dastan.cake.domain.OrderViewModel

@Composable
fun CheckoutScreen(navController: NavController, orderViewModel: OrderViewModel) {
    //val allCakes = orderViewModel.getAllCakes.collectAsState(initial = emptyList()).value
    val total by orderViewModel.totalPrice.collectAsState()
    val name = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val entrance = remember { mutableStateOf("") }
    val doorPhone = remember { mutableStateOf("") }
    val floor = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(color = colorScheme.tertiary)) {
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier.padding(2.dp).offset(y = -8.dp).fillMaxWidth().height(40.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = colorScheme.background), contentAlignment = Alignment.BottomCenter
            ) {
                Row(modifier = Modifier.fillMaxWidth().offset(y = -14.dp).padding(start = 16.dp)) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable { navController.navigateUp() })
                    Text(
                        "Checkout",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Box(
                modifier = Modifier.padding(2.dp).offset(y = -8.dp).fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5))
                    .background(color = colorScheme.background)
            ) {
                Column {
                    Text("Where to", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.home_05), contentDescription = null,
                            modifier = Modifier.height(20.dp).width(20.dp)
                        )
                        CommonTextField(address, "Address")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
                        CommonTextField(entrance, "Entrance")
                        CommonTextField(doorPhone, "Door Phone")
                    }
                    Row (modifier = Modifier.padding(4.dp)){
                        CommonTextField(floor, "Floor")

                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.phone), contentDescription = null,
                            modifier = Modifier.height(20.dp).width(20.dp)
                        )
                        CommonTextField(number, "Phone number")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.user_profile_02), contentDescription = null,
                            modifier = Modifier.height(20.dp).width(20.dp)
                        )
                        CommonTextField(name, "Name")
                    }


                }

            }
            Box(
                modifier = Modifier.padding(2.dp).offset(y = -8.dp).padding(bottom = 4.dp).fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10))
                    .background(color = colorScheme.background)
            ) {
                Column {
                    Text("Payment", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(R.drawable.wallet), contentDescription = null,
                            modifier = Modifier.padding(8.dp).height(20.dp).width(20.dp))
                        Text("Cash")
                    }

                }
            }
        }



        Box(
            modifier = Modifier.offset(y = 16.dp).fillMaxWidth().height(80.dp)
                .clip(shape = RoundedCornerShape(10))
                .background(color = colorScheme.background)
        ) {
            Row {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(total.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Total", fontSize = 12.sp)
                }


                Box(
                    modifier = Modifier.padding(8.dp).fillMaxWidth().height(48.dp)
                        .clip(shape = RoundedCornerShape(20))
                        .background(color = colorScheme.onTertiary).clickable {
                            //Post Api and delete all order

                        }, contentAlignment = Alignment.Center
                ) {
                    Text("Pay", color = colorScheme.background)
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(value: MutableState<String>, text: String) {

    TextField(value = value.value, onValueChange = { value.value = it },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledTextColor = colorScheme.onSurface,
            disabledIndicatorColor = Color.Transparent,
            containerColor = Color.Transparent,
            cursorColor = colorScheme.scrim,
        ), label = { Text(text)} , modifier=Modifier.width(200.dp)
    )


}