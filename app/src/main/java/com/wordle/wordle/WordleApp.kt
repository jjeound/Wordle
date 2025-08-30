package com.wordle.wordle

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WordleApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}