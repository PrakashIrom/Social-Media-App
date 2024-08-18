package com.example.socialmediaapp.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

interface FireBaseContainer{
    val auth: FirebaseAuth
}

class FireBaseContainerImpl: FireBaseContainer{
    override val auth: FirebaseAuth = Firebase.auth
}