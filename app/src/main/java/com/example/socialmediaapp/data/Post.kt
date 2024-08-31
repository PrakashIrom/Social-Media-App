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
    val likes: Map<String, Boolean> = emptyMap(),
    val likeCount: Int = 0,
    val comments: Map<String, Comment> = emptyMap()
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
            "likeCount" to likeCount,
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

