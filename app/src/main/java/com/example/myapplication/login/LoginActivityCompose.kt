package com.example.myapplication.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
fun LoginActivityCompose(navController: NavController, viewModel : LoginViewModel) {
    var id by rememberSaveable { mutableStateOf("") }
    var pw by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(160.dp)) // For vertical spacing at the top


        Spacer(modifier = Modifier.height(16.dp)) // Spacing between image and card


        Column(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = id,
                onValueChange = { id = it },
                singleLine = true,
                placeholder = { Text("ID") },
                leadingIcon = { Icon(
                    painter = painterResource(id = R.drawable.ic_login),
                    contentDescription = "Login Icon",
                ) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor =  Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = pw,
                onValueChange = { pw = it },
                singleLine = true,
                placeholder = { Text("Password") },
                leadingIcon = { Icon(
                    painter = painterResource(id = R.drawable.ic_login_password),
                    contentDescription = "Password Icon"
                ) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor =  Color.LightGray
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp)) // Spacing between card and buttons

        Button(
            onClick = { viewModel.checkLogin(id, pw) },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                Color.LightGray
            )
        ) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Spacing between login and signup buttons

        Button(
            onClick = {navController.navigate("signup")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                Color.DarkGray
            )
        ) {
            Text("Sign Up")
        }
    }
}

@Composable
fun LoginTextField(hint: String, icon: Int, isPassword: Boolean = false) {
    TextField(
        value = "",
        onValueChange = { /* TODO: Handle text change */ },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        },
        placeholder = {
            Text(text = hint, fontSize = 15.sp)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

