package com.example.myapplication.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.DB.Food
import com.example.myapplication.roomDB.Eating

@Composable
fun FoodList(list: List<Food>, onClick:(Food)->Unit) {
    LazyColumn {
        items(list) {food ->
            FoodUI(food, onClick)
        }
    }
}


@Composable
fun FoodUI(food: Food, onClick:(food: Food)->Unit) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {onClick(food)}
            .background(color= Color.LightGray)
            .border(BorderStroke(3.dp, Color.Red))
        ,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(food.foodName, fontSize=25.sp)
        Text(food.kcal.toString(), fontSize=25.sp)
    }
}


@Composable
fun EatingList(list: List<Eating>) {
    LazyColumn {
        items(list) {eating ->
            EatingUI(eating)
        }
    }
}


@Composable
fun EatingUI(eating: Eating) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(color= Color.LightGray)
            .border(BorderStroke(3.dp, Color.Red))
        ,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(eating.foodName, fontSize=25.sp)
        Text(eating.kcal.toString(), fontSize=25.sp)
    }
}