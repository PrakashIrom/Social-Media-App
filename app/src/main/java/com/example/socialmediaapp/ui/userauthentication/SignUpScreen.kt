package com.example.socialmediaapp.ui.userauthentication

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
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmediaapp.viewmodel.AuthenticateViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignUpScreen(navController: NavHostController, viewModel: AuthenticateViewModel=viewModel()) {

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
                text = "Create Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = viewModel.email.value,
                onValueChange = {viewModel.email.value=it},
                label = { Text(text = "Email", color = Color.DarkGray) },
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

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.password.value,
                onValueChange = {viewModel.password.value=it},
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

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.confirmPassword.value,
                onValueChange = {viewModel.confirmPassword.value=it},
                label = { Text(text = "Confirm Password", color = Color.DarkGray) },
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
                onClick = {viewModel.createAccount(navController)},
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Sign Up", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {navController.navigateUp()}) {
                Text(text = "Back to Login", color = Color.DarkGray, fontSize = 16.sp)
            }

            if (viewModel.showErrorDialog.value ) {
                AlertDialog(
                    onDismissRequest = { viewModel.showErrorDialog.value = false },
                    title = { Text("Error") },
                    text = { Text(viewModel.showErrorMessage.value) },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.showErrorDialog.value = false }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            if (viewModel.showSuccessDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.showSuccessDialog.value = false },
                    title = { Text("Success") },
                    text = { Text("Account successfully created!") },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.showSuccessDialog.value = false
                                navController.navigate("signin")
                            }
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
fun TestSignUpScreen() {
    SocialMediaAppTheme {
        val navController = rememberNavController()
        SignUpScreen(navController)
    }

}