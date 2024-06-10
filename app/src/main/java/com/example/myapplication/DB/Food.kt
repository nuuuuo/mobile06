package com.example.myapplication.DB

import androidx.compose.runtime.saveable.listSaver


data class Food(
    val foodCode: String = "",
    val foodName: String,
    val foodType: Int,
    val kcal: Int,
    val carbohydrate: Int,
    val protein: Int,
    val fat: Int
) {
    constructor():this("", "", 0, 0, 0 ,0, 0)
}

val FoodSaver = listSaver<Food, Any>(
    save = {listOf(it.foodCode, it.foodName, it.foodType, it.kcal)},
    restore = { Food(it[0] as String, it[1] as String, it[2] as Int, it[3] as Int, it[4] as Int, it[5] as Int, it[6] as Int) }
)

