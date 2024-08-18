package com.example.socialmediaapp.ui.mainscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmediaapp.viewmodel.ReadWriteNewUserViewModel

@Composable
fun Home(newUserViewModel: ReadWriteNewUserViewModel = viewModel(factory = ReadWriteNewUserViewModel.Factory)){
    Text(text = newUserViewModel.userName.value)
}