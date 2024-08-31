package com.example.socialmediaapp.dependencyinjection

import com.example.socialmediaapp.viewmodel.AuthenticateViewModel
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodel.ReadWriteNewUserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fbModule = module{
    single{Firebase.auth}
    single{Firebase.database.reference}
    viewModel { AuthenticateViewModel(get()) }
    viewModel { PostViewModel(get()) }
    viewModel { ReadWriteNewUserViewModel(get()) }
}