package com.example.socialmediaapp

import android.app.Application
import com.example.socialmediaapp.dependencyinjection.fbModule
import org.koin.core.context.startKoin

class FireBaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(fbModule)
        }
    }

}