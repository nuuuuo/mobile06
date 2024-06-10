package com.example.myapplication.Nav


sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("Home")
    object Comparison: NavRoutes("Comparison")
    object TodayEating: NavRoutes("TodayEating")
    object MyPage: NavRoutes("MyPage")
}