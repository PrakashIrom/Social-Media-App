package com.example.socialmediaapp.data

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

interface FirebaseDBContainer{
    val db: DatabaseReference
}

class FirebaseDBImp: FirebaseDBContainer{
    override val db: DatabaseReference = Firebase.database.reference
}