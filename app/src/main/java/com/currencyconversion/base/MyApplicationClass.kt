package com.currencyconversion.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration

/*
 * Created by Sambhaji Karad on 25/09/2018
*/

class MyApplicationClass : Application() {

    companion object {
        var appContext: Context? = null
            private set
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        MyApplicationClass.appContext = applicationContext!!
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}