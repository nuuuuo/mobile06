package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.DB.Person

@Composable
fun myPage(person: Person, logout: () -> Unit) {
    val spacerModifier = Modifier.height(30.dp)
    val buttonRowModifier = Modifier.background(Color.LightGray)

    var cumulativeIntakeStaticScreen by remember {
        mutableStateOf(false)
    }

    if(cumulativeIntakeStaticScreen)
        cumulativeIntakeStatistics(person.kcal, person.carbohydrate, person.protein, person.fat, person.intake, {cumulativeIntakeStaticScreen = false})
    else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = spacerModifier)
            profile(person.name, person.age, person.height, person.weight)
            Spacer(modifier = spacerModifier)
            myKcal(person.kcal, 20)
            Spacer(modifier = spacerModifier)
            myNutrients(
                carbohydrate = person.carbohydrate,
                protein = person.protein,
                fat = person.fat,
                fontSize = 20
            )

            Spacer(modifier = spacerModifier)
            Row {
                myPageButton(text = "누적 섭취 기록") { cumulativeIntakeStaticScreen = true }
                Spacer(modifier = Modifier.width(20.dp))
                myPageButton(text = "내 정보 수정") {}
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                myPageButton(text = "문의하기") {}
                Spacer(modifier = Modifier.width(20.dp))
                myPageButton(text = "환경 설정") {}
            }

            logoutButton { }
        }
    }

}