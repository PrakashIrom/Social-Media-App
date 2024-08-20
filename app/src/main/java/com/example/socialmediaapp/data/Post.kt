package com.example.socialmediaapp.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    val userId: String = "",
    val userName: String = "",
    val title: String = "",
    val content: String = "",
    val timestamp: Long = 0,
    val likes: Map<String, Boolean> = emptyMap(), // Stores userIds who liked the post
    val likeCount: Int = 0, // Stores the total number of likes
    val comments: Map<String, Comment> = emptyMap() // Stores comments associated with the post
) {
    @Exclude
    fun toMap(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "userName" to userName,
            "title" to title,
            "content" to content,
            "timestamp" to timestamp,
            "likes" to likes,
            "count" to likeCount,
            "comments" to comments
        )
    }
}

@IgnoreExtraProperties
data class Comment(
    val userId: String = "",
    val commentText: String = "",
    val timestamp: Long = 0
) {
    @Exclude
    fun toMap(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "commentText" to commentText,
            "timestamp" to timestamp
        )
    }
}

