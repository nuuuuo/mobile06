package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.DB.Nutrient
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun cumulativeIntakeStatistics(kcal:Int, carbohydrate:Double, protein:Double, fat:Double, intake:Map<LocalDate, Nutrient>, onClick:()->Unit) {

    val days = intake.keys.toList()
    val CarbohydrateIntakeStatistics = intake.values.map { it.carbohydrate }
    val ProteinIntakeStatistics = intake.values.map { it.protein }
    val FatIntakeStatistics = intake.values.map { it.fat }

    Column (
        modifier = Modifier
            .padding(vertical = 10.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ){
        Box( modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = onClick)
            )
        }
        myKcal(kcal = kcal, fontSize = 15)
        myNutrients(carbohydrate = carbohydrate, protein = protein, fat = fat, fontSize = 15)

        Text(text = "누적 섭취 통계량", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)

        LazyColumn {
            item{
                chart(CarbohydrateIntakeStatistics, days)
                chart(ProteinIntakeStatistics, days)
                chart(FatIntakeStatistics, days)
            }
        }
    }
}

@Composable
fun chart(cumulativeIntake: List<Double>, day:List<LocalDate>) {
    val chart = remember { CartesianChartModelProducer.build() }

    LaunchedEffect(Unit) {
        chart.tryRunTransaction { lineSeries { series(cumulativeIntake)} }
    }

    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(
                valueFormatter = { value, _, _ ->
                    day[value.toInt()].format(DateTimeFormatter.ofPattern("MM-dd"))
                }
            ),
        ),
        chart,
        scrollState = rememberVicoScrollState(),
    )
}
