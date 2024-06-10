package com.example.myapplication.Nav

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Home() {
    Column {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Home"
        )
        Text("검색")
    }
}


@Composable
fun Comparison() {
    Column {
        Icon(
            imageVector = Icons.Default.ShoppingBasket,
            contentDescription = "Comparison"
        )
        Text("비교")
    }
}

