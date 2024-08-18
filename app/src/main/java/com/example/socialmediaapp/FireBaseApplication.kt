package com.example.socialmediaapp

import android.app.Application
import com.example.socialmediaapp.data.FireBaseContainerImpl
import com.example.socialmediaapp.data.FirebaseDBImp

class FireBaseApplication: Application() {

    lateinit var firebaseAuth: FireBaseContainerImpl
    lateinit var firebaseDB: FirebaseDBImp
    override fun onCreate() {
        super.onCreate()
        firebaseAuth = FireBaseContainerImpl()
        firebaseDB = FirebaseDBImp()
    }

}