package com.example.myapplication.DB

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
    var intake: Map<LocalDate, Nutrient>
) {
    constructor():this("", "", "", 0, 0.0, 0.0, 0, .0, .0, .0, mapOf())
}
