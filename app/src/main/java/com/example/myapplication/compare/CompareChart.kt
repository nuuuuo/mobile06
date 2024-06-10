package com.example.myapplication.compare

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.myapplication.R
import kotlinx.coroutines.launch

@Composable
fun MultiNutrientBarChart(products: List<Product>, viewModel: ProductViewModel) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val labelMap by remember { mutableStateOf(generateLabelMap(products)) }

    val showButton by remember {
        derivedStateOf {
            state.firstVisibleItemIndex > 0
        }
    }

    // Nutrient selectors
    val nutrientSelectors = mapOf(
        "에너지 (kcal)" to { p: Product -> p.energy },
        "탄수화물 (g)" to { p: Product -> p.carbohydrates },
        "지방 (g)" to { p: Product -> p.fat },
        "단백질 (g)" to { p: Product -> p.protein },
        "당류 (g)" to { p: Product -> p.sugars },
        "포화 지방산 (g)" to { p: Product -> p.saturatedFat },
        "나트륨 (mg)" to { p: Product -> p.sodium },
        "콜레스테롤 (mg)" to { p: Product -> p.cholesterol }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = state) {
            item {
                DisplayLabelMap(labelMap)
            }

            items(nutrientSelectors.size) { index ->
                val title = nutrientSelectors.keys.elementAt(index)
                val selector = nutrientSelectors.values.elementAt(index)
                ExpandableNutrientChart(products, selector, title, labelMap)
            }
        }

//        AnimatedVisibility(visible = showButton) {
//            ScrollToTopButton {
//                scope.launch {
//                    state.scrollToItem(0)
//                }
//            }
//        }

        FloatingActionButton(  // 다이얼로그를 표시하는 FAB
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.List, contentDescription = "Show List")
        }

        if (showDialog) {
            ProductListDialog(products.toMutableList(), onDismissRequest = { showDialog = false }, viewModel)
        }
    }
}

@Composable
fun ExpandableNutrientChart(
    products: List<Product>,
    nutrientSelector: (Product) -> Double,
    title: String,
    labelMap: Map<Product, String>
) {
    var expanded by remember { mutableStateOf(false) }
    val sortedProducts = products.sortedByDescending(nutrientSelector)

    val bars = sortedProducts.map { product ->
        BarChartData.Bar(label = labelMap[product] ?: "",color = Color.Black,  value = nutrientSelector(product).toFloat())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "$title 순위", modifier = Modifier.padding(bottom = 8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        BarChart(
            barChartData = BarChartData(bars = bars),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            barDrawer = SimpleBarDrawer(),
            xAxisDrawer = SimpleXAxisDrawer(
                axisLineThickness = 1.dp,
                axisLineColor = Color.Blue
            ),
            yAxisDrawer = SimpleYAxisDrawer(
                axisLineThickness = 1.dp,
                axisLineColor = Color.Red
            ),
            labelDrawer = SimpleValueDrawer(
                drawLocation = SimpleValueDrawer.DrawLocation.XAxis,
                labelTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (expanded) {
            for (product in sortedProducts) {
                Text("${product.name} (${labelMap[product]})이 ${sortedProducts.indexOf(product) + 1}등 했어요")
            }
        } else {
            for (product in sortedProducts.take(3)) {
                Text("${product.name} (${labelMap[product]})이 ${sortedProducts.indexOf(product) + 1}등 했어요")
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                    contentDescription = if (expanded) "Show Less" else "Show More"
                )
            }
            if(expanded)
                Text("3등 까지만 보기")
            else
                Text("전체 순위 보기")
        }
    }
}
@Composable
fun DisplayLabelMap(labelMap: Map<Product, String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "< 제품 라벨링 >")
        labelMap.forEach { (product, label) ->
            Text(text = "$label - ${product.name}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

fun generateLabelMap(products: List<Product>): Map<Product, String> =
    products.mapIndexed { index, product -> product to ('A' + index).toString() }.toMap()
