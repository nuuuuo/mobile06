package com.example.myapplication.DB

import com.example.myapplication.DB.Nutrient
import java.time.LocalDate

data class Person(
    var id:String,
    var pw:String,
    var name:String,
    var age:Int,
    var height:Double,
    var weight:Double,
    var kcal:Int,
    var carbohydrate:Double,
    var protein:Double,
    var fat:Double,
    var intake: Map<String, Nutrient>
) {
    constructor():this("", "", "", 0, 0.0, 0.0, 0, .0, .0, .0, mapOf())
}
