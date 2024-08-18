package com.example.socialmediaapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class User(
    val userName: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    var userId: String? = ""
)

data class NewUser(
    val userName: String = "",
    val email: String = "",
)
