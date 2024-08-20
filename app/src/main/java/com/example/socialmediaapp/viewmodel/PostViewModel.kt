package com.example.socialmediaapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.socialmediaapp.FireBaseApplication
import com.example.socialmediaapp.data.FirebaseDBImp
import com.example.socialmediaapp.data.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostViewModel(firebaseDB: FirebaseDBImp): ViewModel() {

    private val database = firebaseDB.db
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    private val _key = MutableStateFlow<String?>("")
    val key: StateFlow<String?> = _key
    private val _count = MutableStateFlow<Int?>(0)
    val count: StateFlow<Int?> = _count

    fun writeNewPost(userId: String, post: Post)
    {
        _key.value = database.child("posts").push().key

        if (_key.value == null)
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
                Log.e("Post Id is", "Key: ${_key.value}")
                Log.d("PostViewModel", "Post successfully written.")
            }
            .addOnFailureListener { exception ->
                Log.e("PostViewModel", "Failed to write post", exception)
            }

    }

    fun readNewPost() {
        database.child("posts").addValueEventListener(object : ValueEventListener {
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

    fun toggleLike(postId: String, userId: String) {
        val postRef = database.child("posts").child(postId)

        postRef.child("likes").child("userIds").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // User already liked this post, so unlike it
                    postRef.child("likes").child("userIds").child(userId).removeValue()
                    postRef.child("likes").child("count").setValue(ServerValue.increment(-1))
                } else {
                    // User hasn't liked this post, so like it
                    postRef.child("likes").child("userIds").child(userId).setValue(true)
                    postRef.child("likes").child("count").setValue(ServerValue.increment(1))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PostViewModel", "Failed to toggle like", error.toException())
            }
        })
    }

    fun getLikeCount(postId: String) {
        val postRef = database.child("posts").child(postId).child("likes").child("count")

        postRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retrieve the like count from the snapshot
                _count.value = snapshot.getValue(Int::class.java) ?: 0
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PostViewModel", "Failed to read like count", error.toException())
            }
        })
    }


    fun addComment(postId: String, userId: String, commentText: String) {
        val postRef = database.child("posts").child(postId).child("comments")
        val newCommentRef = postRef.push()

        val comment = mapOf(
            "userId" to userId,
            "commentText" to commentText,
            "timestamp" to ServerValue.TIMESTAMP
        )

        newCommentRef.setValue(comment)
            .addOnSuccessListener {
                Log.d("PostViewModel", "Comment successfully added.")
            }
            .addOnFailureListener { exception ->
                Log.e("PostViewModel", "Failed to add comment", exception)
            }
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