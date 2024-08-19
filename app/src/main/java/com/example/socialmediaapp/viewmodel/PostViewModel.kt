package com.example.socialmediaapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.socialmediaapp.FireBaseApplication
import com.example.socialmediaapp.data.FirebaseDBImp
import com.example.socialmediaapp.data.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostViewModel(firebaseDB: FirebaseDBImp): ViewModel() {

    private val database = firebaseDB.db
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    //val posts = mutableStateListOf<Post>()

    fun writeNewPost(userId: String, post: Post)
    {
        val key = database.child("posts").push().key

        if (key == null)
        {
            Log.w("PostViewModel", "Couldn't get push key for posts")
            return
        }

        val postValues = post.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$userId/$key" to postValues,
        )

        database.updateChildren(childUpdates)
            .addOnSuccessListener {
                Log.d("PostViewModel", "Post successfully written.")
            }
            .addOnFailureListener { exception ->
                Log.e("PostViewModel", "Failed to write post", exception)
            }

    }

    fun readNewPost(userId: String) {
        database.child("user-posts").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = mutableListOf<Post>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    if (post != null) {
                        postList.add(post)
                    }
                }
                _posts.value = postList
                Log.d("User Posts", "Posts: ${_posts.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("UserPosts", "Error reading user posts", error.toException())
            }
        })
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FireBaseApplication)
                PostViewModel(application.firebaseDB)
            }
        }
    }
}