package com.dastan.cake

import android.app.Application

class CakeApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}