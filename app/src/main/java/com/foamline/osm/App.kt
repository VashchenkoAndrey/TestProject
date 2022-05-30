package com.foamline.osm

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log

class App: Application() {

    override fun attachBaseContext(base: Context?) {
        Log.i("##THE_APP", "attachBaseContext: $baseContext")
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        Log.i("##THE_APP", "onCreate")
        super.onCreate()
    }

    override fun onLowMemory() {
        Log.i("##THE_APP", "onLowMemory")
        super.onLowMemory()
    }

    override fun onTerminate() {
        Log.i("##THE_APP", "onTerminate")
        super.onTerminate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.i("##THE_APP", "onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }
}