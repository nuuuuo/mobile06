package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginRepository(private val db: FirebaseFirestore) {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val currentDate: LocalDate = LocalDate.now()

    fun checkLogin(userId: String, password: String): Flow<Person?> = flow {
        try {
            val dateStr = currentDate.format(dateFormatter)
            val userRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection(userId).document(userId)
            val intakeRef = userRef.collection("intake").document(dateStr)

            val userSnapshot = userRef.get().await()
            val intakeSnapshot = intakeRef.get().await()

            val person = userSnapshot.toObject(Person::class.java)
            val intakeData = intakeSnapshot.data as? Map<String, Any>

            intakeData?.let { data ->
                val nutrientMap = data.mapKeys { LocalDate.parse(it.key, dateFormatter) }.mapValues { (_, value) ->
                    val nutrientMap = value as? Map<String, Any>
                    Nutrient(
                        carbohydrate = (nutrientMap?.get("carbohydrate") as? Number)?.toDouble() ?: 0.0,
                        protein = (nutrientMap?.get("protein") as? Number)?.toDouble() ?: 0.0,
                        fat = (nutrientMap?.get("fat") as? Number)?.toDouble() ?: 0.0
                    )
                }

                person?.intake = nutrientMap
            }

            if (person != null && person.pw == password) {
                Log.d("로그인", "성공")
                emit(person)  // Emit Person object if password matches
            } else {
                Log.d("로그인", "실패")
                emit(null)  // Emit null if password does not match or person is null
            }
        } catch (e: Exception) {
            Log.d("로그인", "$e")
            emit(null)  // Emit null on exception
        }
    }
}


//
//class LoginRepository(private val db: FirebaseFirestore) {
//    val date: LocalDate = LocalDate.now()
//    fun checkLogin(userId: String, password: String): Flow<Person?> = flow {
//        try {
//            val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
//            val userRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection(userId).document(userId)
//            val intakeRef = userRef.collection("intake").document(dateStr)
//
//            val userSnapshot = userRef.get().await()
//            val intakeSnapshot = intakeRef.get().await()
//
//            val person = userSnapshot.toObject(Person::class.java)
//            val intakeData = intakeSnapshot.data as? Map<String, Map<String, Any>>
//
//            intakeData?.let { data ->
//                val nutrientMap = data.mapValues { (_, value) ->
//                    val nutrientMap = value as? Map<String, Any>
//                    Nutrient(
//                        carbohydrate = (nutrientMap?.get("carbohydrate") as? Number)?.toDouble() ?: 0.0,
//                        protein = (nutrientMap?.get("protein") as? Number)?.toDouble() ?: 0.0,
//                        fat = (nutrientMap?.get("fat") as? Number)?.toDouble() ?: 0.0
//                    )
//                }
//
//                person?.intake = nutrientMap
//            }
//
////            // Convert Map<String, Any> to Map<String, Nutrient>
////            person?.intake = intakeData?.mapValues { (_, value) ->
////                Nutrient(
////                    carbohydrate = (value["carbohydrate"] as? Number)?.toDouble() ?: 0.0,
////                    protein = (value["protein"] as? Number)?.toDouble() ?: 0.0,
////                    fat = (value["fat"] as? Number)?.toDouble() ?: 0.0
////                )
////            } ?: mapOf()
//
//            if (person != null && person.pw == password) {
//                Log.d("로그인", "성공")
//                emit(person)  // Emit Person object if password matches
//            } else {
//                Log.d("로그인", "실패")
//                emit(null)  // Emit null if password does not match or person is null
//            }
//        } catch (e: Exception) {
//            Log.d("로그인", "$e")
//            emit(null)  // Emit null on exception
//        }
//    }
//}