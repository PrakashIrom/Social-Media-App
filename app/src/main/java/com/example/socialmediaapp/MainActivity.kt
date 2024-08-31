package com.example.socialmediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.ui.userauthentication.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaAppTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

