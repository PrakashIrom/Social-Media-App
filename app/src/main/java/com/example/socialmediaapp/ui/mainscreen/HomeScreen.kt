package com.example.socialmediaapp.ui.mainscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmediaapp.data.Post
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodel.ReadWriteNewUserViewModel

@Composable
fun Home( userId:String, readViewModel: ReadWriteNewUserViewModel=viewModel(factory = ReadWriteNewUserViewModel.Factory),
          postViewModel: PostViewModel = viewModel(factory = PostViewModel.Factory))
{

    LaunchedEffect(userId) {
        readViewModel.readNewUser(userId)
    }
    val userName = readViewModel.userName.value
    val postId by postViewModel.key.collectAsState()

    Scaffold(topBar = { TopBar(userId, userName=userName)}) {
            Post(modifier=Modifier.padding(it), userId=userId, postId = postId!!)
    }

}

@Composable
fun TopBar(userId:String, postViewModel: PostViewModel = viewModel(factory = PostViewModel.Factory), userName:String){

    var text by remember { mutableStateOf("") }

    Column(){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "User",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = userName, fontWeight = FontWeight.W400)

                    Spacer(modifier = Modifier.width(12.dp))

                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = false,
                        decorationBox = { innerTextField ->
                            if (text.isEmpty()) {
                                Text(
                                    text = "Write a post...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.Gray,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                            innerTextField() // Draw the actual input text
                        },
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        val post = Post(userId, userName,"Perseverance", text, 0)
                        postViewModel.writeNewPost(userId, post)
                    }) {
                        Text("Post")
                    }
                }
            }
        }
    }
}
