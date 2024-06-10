package com.example.myapplication.Nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.DB.Food
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import com.example.myapplication.screen.myPage
import com.example.myapplication.screen.todayEating
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.compare.MainCompare
import com.example.myapplication.compare.ProductViewModel
import java.time.LocalDate

@Composable
fun NavigationHost(navController:NavHostController, eatingViewModel: EatingViewModel, productViewModel: ProductViewModel) {
    val intake = mapOf<LocalDate, Nutrient>(
        LocalDate.now() to Nutrient(150.0, 40.0, 30.0)

    )
    val S = Person("id", "pw", "name", 24, 153.0, 41.0, 1600, 210.0, 40.0, 45.0, intake)
    var foodList = rememberSaveable { mutableStateOf(listOf<Food>()) }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ){
        composable(NavRoutes.Home.route){
            Home()
        }
        composable(NavRoutes.Comparison.route){
            MainCompare(productViewModel)
        }
        composable(NavRoutes.TodayEating.route){
            todayEating(S.kcal, S.carbohydrate, S.protein, S.fat, eatingViewModel)
        }
        composable(NavRoutes.MyPage.route) {
            myPage(S, { })
        }
    }
}