package com.example.socialmediaapp

import android.app.Application
import com.example.socialmediaapp.data.FireBaseContainerImpl

class FireBaseApplication: Application() {

    lateinit var firebaseAuth: FireBaseContainerImpl

    override fun onCreate() {
        super.onCreate()
        firebaseAuth = FireBaseContainerImpl()
    }

}