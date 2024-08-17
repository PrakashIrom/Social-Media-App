package com.example.socialmediaapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth

class AuthenticateViewModel: ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val showErrorDialog = mutableStateOf(false)
    val showSuccessDialog = mutableStateOf(false)
    val showErrorMessage = mutableStateOf("")
    val auth: FirebaseAuth = Firebase.auth

    fun login(navController: NavHostController) {
        val auth: FirebaseAuth = com.google.firebase.Firebase.auth

        if (email.value.isEmpty() || password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("home")
                } else {
                    showErrorMessage.value = "Login failed. Please try again."
                    showErrorDialog.value = true
                }
            }.addOnFailureListener{
                    exception ->
                when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        showErrorMessage.value = "Invalid password. Please try again."
                        showErrorDialog.value = true
                    }
                    is FirebaseAuthInvalidUserException -> {
                        showErrorMessage.value = "No account found with this email. Please sign up first."
                        showErrorDialog.value = true
                    }
                    is FirebaseNetworkException -> {
                        showErrorMessage.value = "Network error. Please check your connection and try again."
                        showErrorDialog.value = true
                    }
                    else -> {
                        showErrorMessage.value = "An unexpected error occurred: ${exception.message}"
                        showErrorDialog.value = true
                    }
                }

            }
    }

    fun createAccount(){

        if (email.value.isEmpty() || password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        else if(password.value!=confirmPassword.value){
            showErrorMessage.value = "Password does not match!"
            showErrorDialog.value = true
            return
        }

        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSuccessDialog.value = true

                } else {
                    showErrorMessage.value="Sign In failed try again!"
                    showErrorDialog.value = true
                }
            }.addOnFailureListener { exception ->
                if (exception is FirebaseAuthUserCollisionException) {
                    showErrorMessage.value = "The email address is already in use by another account."
                    showErrorDialog.value = true
                } else {
                    showErrorMessage.value = "An error occurred: ${exception.message}"
                    showErrorDialog.value = true
                }
            }
    }

}