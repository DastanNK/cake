package com.dastan.cake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dastan.cake.data.CakeInfo
import com.dastan.cake.data.Screens
import com.dastan.cake.domain.FirebaseViewModel
import com.dastan.cake.domain.InfoViewModel
import com.dastan.cake.domain.OrderViewModel
import com.dastan.cake.screen.CartScreen
import com.dastan.cake.screen.CustomScreen
import com.dastan.cake.screen.EachCakeScreen
import com.dastan.cake.screen.HomeScreen
import com.dastan.cake.ui.theme.CakeTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(){
    val navController= rememberNavController()
    val infoViewModel:InfoViewModel= viewModel()
    val firebaseViewModel = hiltViewModel<FirebaseViewModel>()
    val orderViewModel:OrderViewModel= viewModel()
    NavHost(navController=navController, startDestination = Screens.HomeScreen.route){
        composable(Screens.HomeScreen.route){
            HomeScreen(navController, infoViewModel)
        }
        composable(
            route = Screens.EachCakeScreen.route,
            arguments = listOf(navArgument("cakeInfo") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val cakeJson = navBackStackEntry.arguments?.getString("cakeInfo")
            val cakeInfo = Gson().fromJson(cakeJson, CakeInfo::class.java)
            EachCakeScreen(firebaseViewModel, navController, cakeInfo, orderViewModel)
        }
        composable(Screens.CartScreen.route){
            CartScreen(navController, orderViewModel)
        }
        composable(Screens.CustomScreen.route){
            CustomScreen(navController, orderViewModel)
        }
    }

}
