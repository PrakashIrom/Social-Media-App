package com.example.socialmediaapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.socialmediaapp.FireBaseApplication
import com.example.socialmediaapp.data.FirebaseDBImp
import com.example.socialmediaapp.data.NewUser

class ReadWriteNewUserViewModel(dbImp: FirebaseDBImp): ViewModel() {

    private val database = dbImp.db
    val userName = mutableStateOf("")
    fun writeNewUser(userId: String, name: String, email: String) {
        Log.d("userId", userId)
        val user = NewUser( name, email)
       database.child("users").child(userId).setValue(user)
    }

    fun readNewUser(userId: String){
        database.child("users").child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val name = snapshot.child("userName").getValue(String::class.java)
                userName.value = name!!
            } else {
                Log.e("ReadWriteNewUserViewModel", "User with ID $userId does not exist")
            }
        }.addOnFailureListener { exception ->
            Log.e("ReadWriteNewUserViewModel", "Error reading data", exception)
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FireBaseApplication)
                ReadWriteNewUserViewModel(application.firebaseDB)
            }
        }
    }

}