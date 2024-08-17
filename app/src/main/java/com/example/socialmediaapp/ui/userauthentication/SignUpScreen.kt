package com.example.socialmediaapp.ui.userauthentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme

@Composable
fun SignUpScreen(onSignUp: () -> Unit, onBackToLogin: () -> Unit) {
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

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Email", color = Color.DarkGray) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .background(Color.White, RoundedCornerShape(35.dp)),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Password", color = Color.DarkGray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .background(Color.White, RoundedCornerShape(35.dp)),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Confirm Password", color = Color.DarkGray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .background(Color.White, RoundedCornerShape(35.dp)),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Sign Up", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackToLogin) {
                Text(text = "Back to Login", color = Color.DarkGray, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestSignUpScreen() {
    SocialMediaAppTheme {
        SignUpScreen(onSignUp = {}, onBackToLogin = {})
    }

}