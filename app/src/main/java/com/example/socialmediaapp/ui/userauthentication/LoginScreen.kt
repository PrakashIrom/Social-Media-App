package com.example.socialmediaapp.ui.userauthentication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.viewmodel.AuthenticateViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.socialmediaapp.ui.mainscreen.Home
import com.example.socialmediaapp.viewmodel.ReadWriteNewUserViewModel

@Composable
fun LoginScreen(navController: NavHostController,
                viewModel: AuthenticateViewModel=viewModel(factory = AuthenticateViewModel.Factory))
 {

    val user = viewModel.user.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0BCFF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = user.email.value,
                onValueChange = {user.email.value=it},
                label = { Text(text = "Email", color = Color.DarkGray) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = user.password.value,
                onValueChange = {user.password.value=it},
                label = { Text(text = "Password", color = Color.DarkGray) },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    viewModel.login(navController)
                          },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Login", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {}) {
                Text(text = "Forgot Password?", color = Color.DarkGray, fontSize = 16.sp)
            }

            TextButton(onClick = {navController.navigate("register")}) {
                Text(text = "Create Account", color = Color.DarkGray, fontSize = 16.sp)
            }
            if (viewModel.showErrorDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.showErrorDialog.value = false },
                    title = { Text("Error") },
                    text = { Text("Invalid username or password") },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.showErrorDialog.value = false }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestLogin(){
    SocialMediaAppTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController=navController, startDestination = "login"){
        composable("login"){
            LoginScreen(navController)
        }
        composable("register"){
            SignUpScreen(navController)
       }
        composable("home/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ){
                backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            Home(userId = userId!!)
        }
    }
}

