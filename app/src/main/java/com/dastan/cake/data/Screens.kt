package com.dastan.cake.data

import android.net.Uri
import com.google.gson.Gson

sealed class Screens (val route:String){
    object EachCakeScreen : Screens("EachCake/{cakeInfo}") {
        fun createRoute(cakeInfo: CakeInfo): String = "EachCake/${Uri.encode(Gson().toJson(cakeInfo))}"
    }
    object HomeScreen: Screens("Home")
}