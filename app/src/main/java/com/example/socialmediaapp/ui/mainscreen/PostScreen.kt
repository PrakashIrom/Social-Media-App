package com.example.socialmediaapp.ui.mainscreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialmediaapp.data.Post
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.viewmodel.PostViewModel

@Composable
fun Post(modifier: Modifier, viewModel: PostViewModel, postId:String, userId:String) {

    viewModel.readNewPost()
    val posts by viewModel.posts.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(posts) { post ->
            PostItem(post, viewModel, postId, userId)
        }
    }
}

@Composable
fun PostItem(post: Post,postViewModel: PostViewModel, postId: String, userId: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Display the title
        Text(
            text = post.title,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Display the content
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Display the user's name
        Text(
            text = "Posted by: ${post.userName}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        // Add a divider between posts
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // UI for like, comment, and share
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LikeButton(postId=postId, viewModel = postViewModel,userId = userId)
            CommentButton(post = post, userId = userId)
            ShareButton(post = post)
        }
    }
}

@Composable
fun LikeButton(viewModel: PostViewModel, postId:String, userId: String) {

    val isLiked = remember { mutableStateOf(false) }
    LaunchedEffect(postId){
        viewModel.getLikeCount(postId)
    }
    val likeCount by viewModel.count.collectAsState()

    Row(
        modifier = Modifier.clickable {
            isLiked.value = !isLiked.value
            viewModel.toggleLike(postId, userId)
            // Call a function to update the likes in the database
            // viewModel.updateLikes(post, isLiked.value)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = if (isLiked.value) Color.Red else Color.Gray
        )
        Text(
            text = likeCount.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun CommentButton(post: Post, userId: String) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.clickable {
            // Navigate to comment screen or open a dialog to add a comment
            // For simplicity, we'll just show a toast here
            Toast.makeText(context, "Comment clicked", Toast.LENGTH_SHORT).show()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null,
            tint = Color.Gray
        )
        Text(
            text = "Comment",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun ShareButton(post: Post) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.clickable {
            // Handle share functionality
            // For simplicity, we'll just show a toast here
            Toast.makeText(context, "Share clicked", Toast.LENGTH_SHORT).show()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            tint = Color.Gray
        )
        Text(
            text = "Share",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestPostScreen() {
    SocialMediaAppTheme {
        //Post(modifier = Modifier.fillMaxSize())
    }
}
