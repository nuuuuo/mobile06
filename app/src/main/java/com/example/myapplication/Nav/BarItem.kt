package com.example.myapplication.Nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RiceBowl
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem(val title: String, val selectedIcon: ImageVector, val onSelectedIcon:ImageVector, val route: String)

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Home",
            selectedIcon = Icons.Default.Home,
            onSelectedIcon = Icons.Outlined.Home,
            route = "Home"
        ),
        BarItem(
            title = "Comparison",
            selectedIcon = Icons.Default.ShoppingBasket,
            onSelectedIcon = Icons.Outlined.ShoppingBasket,
            route = "Comparison"
        ),
        BarItem(
            title = "TodayEating",
            selectedIcon = Icons.Default.RiceBowl,
            onSelectedIcon = Icons.Outlined.RiceBowl,
            route = "TodayEating"
        ),
        BarItem(
            title = "MyPage",
            selectedIcon = Icons.Default.Person,
            onSelectedIcon = Icons.Outlined.Person,
            route = "MyPage"
        )
    )
}