package com.example.myapplication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.LoginViewModel

@Composable
fun SignupActivityCompose(navController: NavController, viewModel : LoginViewModel) {
    var name by rememberSaveable { mutableStateOf("") }
    var id by rememberSaveable { mutableStateOf("") }
    var pw by rememberSaveable { mutableStateOf("") }
    var pwCheck by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("계정 만들기", fontSize = 20.sp, color = Color.Gray,  fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") },
            placeholder = { Text("홍길동") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("아이디") },
            placeholder = { Text("ID") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = pw,
            onValueChange = { pw = it },
            label = { Text("비밀번호") },
            placeholder = { Text("비밀번호") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = pwCheck,
            onValueChange = { pwCheck = it },
            label = { Text("비밀번호 확인") },
            placeholder = { Text("비밀번호 확인") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle save */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color.LightGray)
        ) {
            Text("회원가입", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(label: String, placeholder: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        TextField(
            value = placeholder,
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(Color.Transparent)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(label: String, placeholder: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        TextField(
            value = placeholder,
            onValueChange = {},
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(Color.Transparent)
        )
    }
}