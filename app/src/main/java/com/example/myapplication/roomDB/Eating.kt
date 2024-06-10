package com.example.myapplication.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Eating(
    @PrimaryKey(autoGenerate = false)
    val foodCode: String = "",
    val foodName: String,
    val foodType: Int,
    var kcal: Int,
    var carbohydrate: Int,
    var protein: Int,
    var fat: Int,
    var gram: Double
)
