package com.dastan.cake

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CakeApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}