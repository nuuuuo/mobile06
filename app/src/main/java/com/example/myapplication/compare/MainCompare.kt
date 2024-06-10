package com.example.myapplication.compare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainCompare(viewmodel: ProductViewModel = viewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Example of toggling views
        var showDetails by remember { mutableStateOf(true) }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { showDetails = true }) {
                Text("제품 자세히 보기")
            }
            Button(onClick = { showDetails = false }) {
                Text("성분별 순위 보기")
            }
        }

        if (showDetails) {
            ProductList(products = viewmodel.products, viewModel = viewmodel)
        } else {
            MultiNutrientBarChart(products = viewmodel.products, viewModel = viewmodel)
        }
    }
}