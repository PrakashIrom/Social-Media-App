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
        Log.d("ReadWriteNewUserViewModel", "Writing new user: userId=$userId, name=$name, email=$email")
        val user = NewUser( name, email)
        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                Log.d("ReadWriteNewUserViewModel", "User data saved successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("ReadWriteNewUserViewModel", "Failed to save user data", exception)
            }
    }

    fun readNewUser(userId: String){
        Log.d("Pui Id", userId)
        database.child("users").child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val name = snapshot.child("userName").getValue(String::class.java)
                Log.d("ReadWriteNewUserViewModel", "Retrieved user name: $name")
                if (name != null) {
                    userName.value = name
                } else {
                    Log.e("ReadWriteNewUserViewModel", "User name is null")
                }
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