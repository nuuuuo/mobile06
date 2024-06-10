package com.example.myapplication.compare

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class Product(
    val name: String,
    val energy: Double,            // 에너지 (kcal)
    val carbohydrates: Double,     // 탄수화물 (g)
    val sugars: Double,            // 총당류 (g)
    val fat: Double,               // 지방 (g)
    val protein: Double,           // 단백질 (g)
    val sodium: Double,            // 나트륨 (mg)
    val saturatedFat: Double,      // 총 포화 지방산 (g)
    val cholesterol: Double        // 콜레스테롤 (mg)
)

val dailyIntakeStandards = mapOf(
    "에너지" to 2000.0,
    "탄수화물" to 324.0,
    "당류" to 100.0,
    "단백질" to 55.0,
    "지방" to 54.0,
    "포화지방" to 15.0,
    "나트륨" to 2000.0,
    "콜레스테롤" to 300.0
)

@Composable
fun ProductList(products: List<Product>, viewModel: ProductViewModel) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val showButton by remember {
        derivedStateOf { state.firstVisibleItemIndex > 0 }
    }


//    LazyColumn {
//        items(products) { product ->
//            ProductItem(product, onRemove = { viewModel.removeProduct(product) })
//        }
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = state) {
            items(products) { product ->
                ProductDescription(product)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        AnimatedVisibility(visible = showButton) {
            ScrollToTopButton {
                scope.launch {
                    state.scrollToItem(0)
                }
            }
        }

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
fun ProductItem(product: Product, onRemove: () -> Unit) {
    // Layout for product, with a remove button
    Row {
        Text(product.name)
        Button(onClick = onRemove) {
            Text("Remove")
        }
    }
}

@Composable
fun ProductListDialog(products: MutableList<Product>, onDismissRequest: () -> Unit,  viewModel: ProductViewModel) {
    // It's important to create this state list inside the composable to react to changes correctly.
    val safeProducts = remember { mutableStateListOf<Product>().also { it.addAll(products) } }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Manage Products") },
        text = {
            LazyColumn {
                itemsIndexed(safeProducts, key = { index, item -> item.name }) { index, product ->
                    SwipeToDeleteItem(
                        product = product,
                        onDismissed = {
                            // safeProducts 리스트에서 제품을 직접 제거합니다.
                            safeProducts.removeAt(index)
                            // ViewModel의 removeProduct 메서드를 호출하여 제품을 제거합니다.
                            viewModel.removeProduct(product)
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) { Text("Close") }
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}



@Composable
fun ProductDescription(product: Product) {
    var userInput by remember { mutableStateOf("100") }  // Default and user input amount in grams
    var applyRecalculation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(8.dp)
        ) {
            Text(
                text = "${product.name}의 영양성분 정보입니다.",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("영양성분", fontSize = 13.sp, fontWeight = FontWeight.Bold)

                BasicTextField(
                    value = userInput,
                    onValueChange = {userInput = it },
                    singleLine = true,
                    textStyle =  TextStyle(color = Color.Black, fontSize = 13.sp, textAlign = TextAlign.Center),
                    modifier = Modifier
                        .width(50.dp)
                        .offset(x = (12).dp)
                        .padding(4.dp)  // Add padding inside the text field for better text visibility
                        .background(
                            Color.White,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )  // Add a background color
                        .border(1.dp, Color.Black),  // Add a border
                    keyboardOptions =  KeyboardOptions(
                                                            imeAction = ImeAction.Done,
                                                            keyboardType = KeyboardType.Number),
                    decorationBox = { innerTextField ->  // Optional: DecorationBox can be used for additional decorations
                        innerTextField()  // This is where the actual TextField gets displayed
                    }
                )

                Text("g 당 함량", fontSize = 13.sp, fontWeight = FontWeight.Bold)
//                ApplyBadge { // Use the custom badge for apply action
//                    applyRecalculation = !applyRecalculation
//                }
                Text("1일 영양섭취 기준(%)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            NutrientInfoRow("에너지", product.energy, "kcal", userInput, applyRecalculation)
            NutrientInfoRow("탄수화물", product.carbohydrates, "g", userInput, applyRecalculation)
            NutrientInfoRow("총당류", product.sugars, "g", userInput, applyRecalculation)
            NutrientInfoRow("지방", product.fat, "g", userInput, applyRecalculation)
            NutrientInfoRow("단백질", product.protein, "g", userInput, applyRecalculation)
            NutrientInfoRow("나트륨", product.sodium, "mg", userInput, applyRecalculation)
            NutrientInfoRow("포화지방", product.saturatedFat, "g", userInput, applyRecalculation)
            NutrientInfoRow("콜레스테롤", product.cholesterol, "mg", userInput, applyRecalculation)
        }
    }
}

@Composable
fun CustomTextField(userInput: String, onUserInputChange: (String) -> Unit) {
    BasicTextField(
        value = userInput,
        onValueChange = onUserInputChange,
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 12.sp, textAlign = TextAlign.Center),
        modifier = Modifier
            .width(50.dp)
            .background(Color.White, shape = CircleShape)
            .border(1.dp, Color.Black)
            .padding(vertical = 4.dp, horizontal = 0.dp),  // Adjust padding for vertical alignment

        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),

        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {  // Ensure the text is centered
                innerTextField()
            }
        }
    )
}


@Composable
fun ApplyBadge(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .width(30.dp)
            .clickable(onClick = onClick),
        color = Color.Blue, // Set the color of the badge
        shape = CircleShape // Make the badge circular
    ) {
        Text(
            text = "적용", // Use a simple icon-like text or Unicode symbol
            fontSize = 10.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun NutrientInfoRow(nutrientName: String, amountPer100g: Double, unit: String, userInput: String, applyRecalculation: Boolean) {
    var displayedAmount by remember { mutableStateOf(amountPer100g) }

    LaunchedEffect(userInput, applyRecalculation) {
        val inputGrams = userInput.toDoubleOrNull() ?: 100.0
        displayedAmount = amountPer100g * inputGrams / 100
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = nutrientName, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Text(text = "${displayedAmount.format(2)} $unit", fontSize = 14.sp, modifier = Modifier.weight(1f))
        Text(text = "${calculateDailyPercentage(displayedAmount, nutrientName).format(2)}%", fontSize = 14.sp, modifier = Modifier.weight(1f))
    }
}


// Helper function to calculate the percentage of daily intake
fun calculateDailyPercentage(amount: Double, nutrientName: String): Double {
    val dailyIntake = dailyIntakeStandards[nutrientName] ?: return 0.0
    return if (dailyIntake > 0) (amount / dailyIntake) * 100 else 0.0
}

// Extension function to format doubles
fun Double.format(digits: Int): String = "%.${digits}f".format(this)
