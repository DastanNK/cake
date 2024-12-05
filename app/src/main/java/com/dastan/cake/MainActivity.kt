package com.dastan.cake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dastan.cake.data.CakeInfo
import com.dastan.cake.data.Screens
import com.dastan.cake.domain.InfoViewModel
import com.dastan.cake.screen.EachCakeScreen
import com.dastan.cake.screen.HomeScreen
import com.dastan.cake.ui.theme.CakeTheme
import com.google.gson.Gson

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
    val viewModel:InfoViewModel= viewModel()
    NavHost(navController=navController, startDestination = Screens.HomeScreen.route){
        composable(Screens.HomeScreen.route){
            HomeScreen(navController, viewModel)
        }
        composable(
            route = Screens.EachCakeScreen.route,
            arguments = listOf(navArgument("cakeInfo") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val cakeJson = navBackStackEntry.arguments?.getString("cakeInfo")
            val cakeInfo = Gson().fromJson(cakeJson, CakeInfo::class.java)
            EachCakeScreen(viewModel, navController, cakeInfo)
        }
    }

}
