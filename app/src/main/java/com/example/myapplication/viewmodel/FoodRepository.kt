package com.example.myapplication.viewmodel

import com.example.myapplication.DB.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FoodRepository (private val table:DatabaseReference){

    fun searchFood(foodName: String): Flow<List<Food>> = callbackFlow {
        val listener = object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foodList = mutableListOf<Food>()
                for(foodSnapshot in snapshot.children) {
                    val food = foodSnapshot.getValue(Food::class.java)
                    food?.let{foodList.add(food)}
                }
                trySend(foodList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        val query = table.orderByChild("foodName").equalTo(foodName)  // Assuming the field to filter is 'name'

        query.addValueEventListener(listener)
        awaitClose{
            query.removeEventListener(listener)
        }
    }
}