package com.example.socialmediaapp.ui.mainscreen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmediaapp.viewmodel.AuthenticateViewModel
import com.example.socialmediaapp.viewmodel.ReadWriteNewUserViewModel

@Composable
fun Home(newUserViewModel: ReadWriteNewUserViewModel = viewModel(factory = ReadWriteNewUserViewModel.Factory),
         viewModel: AuthenticateViewModel= viewModel(factory = AuthenticateViewModel.Factory), userId:String?
         ){

    LaunchedEffect(userId) {
        userId?.let {
            // Log.d("PuiId", it)
            newUserViewModel.readNewUser(it)
        }
    }
        Text(text=newUserViewModel.userName.value)
}