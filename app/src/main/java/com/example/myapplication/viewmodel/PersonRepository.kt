package com.example.myapplication.viewmodel

import com.example.myapplication.DB.Nutrient
import com.google.firebase.database.DatabaseReference
import java.time.LocalDate

class PersonRepository(private val table:DatabaseReference) {

    fun getPerson(personID:String){
        table.child("id").get()
    }

    fun updatePersonIntake(intake:Map<LocalDate, Nutrient>){
        table.child("intake").setValue(intake)
    }

    fun updatePersonNutreinet(kcal:Int, nutrient: Nutrient){
        table.child("kcal").setValue(kcal)
        table.child("carbohydrate").setValue(nutrient.carbohydrate)
        table.child("protein").setValue(nutrient.protein)
        table.child("fat").setValue(nutrient.fat)
    }
}