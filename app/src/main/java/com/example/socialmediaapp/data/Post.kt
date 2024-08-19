package com.example.socialmediaapp.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    val userId: String = "",
    val userName: String = "",
    val title: String = "",
    val content: String = "",
    val timestamp: Long = 0
){
    @Exclude
    fun toMap(): Map<String, Any> {

        return mapOf(
            "userId" to userId,
            "title" to title,
            "content" to content,
            "timestamp" to timestamp)

    }
}
