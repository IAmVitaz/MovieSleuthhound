package com.vitaz.moviesleuthhound

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(applicationContext)
    }
}
