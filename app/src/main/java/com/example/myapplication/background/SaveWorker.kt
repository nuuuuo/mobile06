package com.example.myapplication.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.roomDB.Eating
import com.example.myapplication.roomDB.EatingDatabase
import com.example.myapplication.viewmodel.EatingRepository
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.PersonRepository
import com.example.myapplication.viewmodel.PersonViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

class SaveWorker(ctx:Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {

            val table = Firebase.database.getReference("Products/people")

            val personViewModel = PersonViewModel(PersonRepository(table))
            personViewModel.getPerson("id")
            val eatingDatabase = EatingDatabase.getEatingDatabase(applicationContext)

            var eating = MutableStateFlow<List<Eating>>(emptyList())
            eatingDatabase.getDao().getAllEating().collect{eating.value = it}

            var todayCarbohydrate = .0
            var todayProtein = .0
            var todayFat = .0

            for(e in eating.value){
                todayCarbohydrate += (e.carbohydrate * (e.gram/100))
                todayProtein += (e.protein * (e.gram/100))
                todayFat += (e.fat * (e.gram/100))
            }

            if(todayCarbohydrate == .0 && todayProtein == .0 && todayFat == .0){  //오늘 사용자가 todayEating을 쓰지 않았을 경우
                return Result.success()
            }

            personViewModel.updatePersonIntake(mapOf(LocalDate.now().minusDays(1) to Nutrient(todayCarbohydrate, todayProtein, todayFat)))

            EatingViewModel(EatingRepository(eatingDatabase)).deleteAllEating()

            Result.success()
        } catch (e:Exception){
            Result.failure()
        }
    }
}
