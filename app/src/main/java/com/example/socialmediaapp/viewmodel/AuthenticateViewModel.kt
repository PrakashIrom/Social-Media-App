package com.example.socialmediaapp.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthenticateViewModel(private val firebaseInst:FireBaseContainerImpl): ViewModel() {

    val showErrorDialog = mutableStateOf(false)
    val showSuccessDialog = mutableStateOf(false)
    val showErrorMessage = mutableStateOf("")
    val confirmPassword: MutableState<String> = mutableStateOf("")
    private val auth: FirebaseAuth = firebaseInst.auth
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user
    private val userDetails = _user.value

    fun login(navController: NavHostController) {

        if (_user.value.email.value.isEmpty() || userDetails.password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        auth.signInWithEmailAndPassword(userDetails.email.value, userDetails.password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userDetails.userId = auth.currentUser?.uid
                    if (userDetails.userId != null) {
                        showErrorDialog.value = false
                        navController.navigate("home/${userDetails.userId}")
                    }

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

        if (userDetails.email.value.isEmpty() || userDetails.password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        else if(userDetails.password.value!= confirmPassword.value){
            showErrorMessage.value = "Password does not match!"
            showErrorDialog.value = true
            return
        }

        auth.createUserWithEmailAndPassword(userDetails.email.value, userDetails.password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userDetails.userId = auth.currentUser?.uid
                    showSuccessDialog.value = true
                    // call a function here to save the user infos like name, email, userId to database
                    navController.navigateUp()
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