package com.example.anyphoto.activities

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var context:Application
    }
    init {
        context = this
    }

}