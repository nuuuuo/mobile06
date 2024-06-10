package com.example.myapplication.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun profile(name:String, age:Int, height: Double, weight: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 14.dp, end = 14.dp)
            .border(3.dp, color = Color.Red)
            .background(Color.Gray)
    ) {
        Box(
            modifier = Modifier.padding(14.dp)
        ) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                Modifier.size(120.dp)
            )
        }
        Column (
            modifier = Modifier.padding(vertical = 14.dp),
            horizontalAlignment = Alignment.Start
        ){
            Row (
                verticalAlignment = Alignment.Bottom
            ){
                Text(text = "$name", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(text = " 님")
            }
            Text(text = "나이: 만 ${age}세")
            Text(text = "키: ${height}cm")
            Text(text = "몸무게: ${weight}kg")
        }
    }
}

@Composable
fun myKcal(kcal:Int, fontSize:Int) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 14.dp)
            .border(BorderStroke(3.dp, Color.Red), CircleShape)
            .background(Color.LightGray, CircleShape)
            .padding(horizontal = 15.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("나의 기초대사량", fontSize = fontSize.sp)
        Text("${kcal}kcal", fontSize = fontSize.sp)
    }
}

@Composable
fun myNutrients(carbohydrate:Double, protein:Double, fat:Double, fontSize: Int) {
    val columnModifier = Modifier
        .padding(horizontal = 30.dp, vertical = 14.dp)
        .height((fontSize*3 + 25).dp)

    Row (
        modifier = Modifier
            .wrapContentHeight()
            .border(BorderStroke(3.dp, color = Color.Red), CircleShape)
            .background(Color.LightGray, CircleShape)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier,
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(text = "탄수화물", fontSize = fontSize.sp)
            Text(text = "단백질", fontSize = fontSize.sp)
            Text(text = "지방", fontSize = fontSize.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier,
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(text = "${carbohydrate}g")
            Text(text = "${protein}g")
            Text(text = "${fat}g")
        }
    }
}
