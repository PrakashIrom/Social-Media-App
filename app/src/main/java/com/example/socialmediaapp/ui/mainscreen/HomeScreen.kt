package com.example.socialmediaapp.ui.mainscreen


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmediaapp.data.Post
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodel.ReadWriteNewUserViewModel

@Composable
fun Home( userId:String, viewModel: PostViewModel = viewModel(factory = PostViewModel.Factory)){

    /*
    LaunchedEffect(userId) {
        userId.let {
            newUserViewModel.readNewUser(it)
        }
    }*/
    val post = Post(userId,"Pk", "Grind", "Hello I am grinding", 0)

    LaunchedEffect(userId) {
        viewModel.writeNewPost(userId, post)
        viewModel.readNewPost(userId)
    }

    val posts = viewModel.posts.collectAsState()

    LazyColumn {
        items(posts.value) { post ->
            Text(text = post.title)
        }
    }

}
