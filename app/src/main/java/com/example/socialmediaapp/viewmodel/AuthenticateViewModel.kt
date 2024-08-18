package com.example.socialmediaapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.socialmediaapp.FireBaseApplication
import com.example.socialmediaapp.data.FireBaseContainerImpl
import com.example.socialmediaapp.data.User
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class AuthenticateViewModel(private val firebaseInst:FireBaseContainerImpl): ViewModel() {

    val showErrorDialog = mutableStateOf(false)
    val showSuccessDialog = mutableStateOf(false)
    val showErrorMessage = mutableStateOf("")
    val confirmPassword: MutableState<String> = mutableStateOf("")
    private val auth: FirebaseAuth = firebaseInst.auth
    val user = User()

    fun login(navController: NavHostController) {

        if (user.email.value.isEmpty() || user.password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        auth.signInWithEmailAndPassword(user.email.value, user.password.value)
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

    fun createAccount(navController: NavHostController){

        if (user.email.value.isEmpty() || user.password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        else if(user.password.value!= confirmPassword.value){
            showErrorMessage.value = "Password does not match!"
            showErrorDialog.value = true
            return
        }

        auth.createUserWithEmailAndPassword(user.email.value, user.password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSuccessDialog.value = true
                    user.userId = auth.currentUser?.uid
                    // call a function here to save the user infos like name, email, userId to database
                    navController.navigate("login")
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

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                    val application =(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FireBaseApplication)
                AuthenticateViewModel(application.firebaseAuth)
            }
        }
    }

}