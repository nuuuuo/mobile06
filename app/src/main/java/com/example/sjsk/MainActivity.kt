package com.example.sjsk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sjsk.firestore.ApiResponse
import com.example.sjsk.firestore.RetrofitClient
import com.example.sjsk.ui.theme.SJSKTheme
import com.example.sjsk.viewModel.Repository
import com.example.sjsk.viewModel.SearchViewModel
import com.example.sjsk.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SJSKTheme {
//                val mainViewModel: SjskViewModel = viewModel()
//                MyAppScreen(mainViewModel)

                val viewModel: SearchViewModel = viewModel(factory = ViewModelFactory(Repository(RetrofitClient.instance)))
                MyAppScreen(viewModel)

            }
        }
    }
}


@Composable
fun MyAppScreen(viewModel: SearchViewModel) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search Food") },
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.searchFood(searchText) },
                modifier = Modifier.height(IntrinsicSize.Min)
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        val data by viewModel.data.collectAsState()

        if (data != null) {
            DataDisplay(data = data!!, searchText)
            Spacer(modifier = Modifier.height(8.dp))
            PageIndicator(viewModel)
        } else {
            Text("No items available")
        }
    }
}


@Composable
fun DataDisplay(data: ApiResponse, searchText: String) {
    // 데이터를 보여주는 간단한 카드 Composable
    if (data.body?.items?.isNotEmpty() == true) {  // 데이터가 있을 때
        Column {
            Text("\"$searchText\"에 대한 검색 결과입니다.", modifier = Modifier.fillMaxWidth(), fontSize = 20.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(30.dp))

            HeaderBar()

            data.body?.items?.forEach { items ->
                items.item?.forEachIndexed { index, item ->
                    Row {
                        Text(text = "${data.body?.pageNo?.toInt()?.minus(1)?.times(10)?.plus(index+1)}", modifier = Modifier.weight(1f))
                        Text(text = "${item.FOOD_NM_KR}", modifier = Modifier.weight(3f))
                        Text(text = "${item.AMT_NUM1}", modifier = Modifier.weight(2f))
                        Button(
                            onClick = { /*TODO*/ },
                            shape = CircleShape,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                                contentDescription = "비교하기"
                            )
                        }
                    }
                }
            }
        }
    } else {
        Text("No items available")
    }
}

@Composable
fun PageIndicator(viewModel: SearchViewModel) {
    val totalPages = viewModel.getTotalPages()
    val data by viewModel.data.collectAsState()
    val currentPage = data?.body?.pageNo?.toInt() ?: 1

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (page in 1..totalPages) {
            Text(
                text = page.toString(),
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { viewModel.changePage(page) },
                fontWeight = if (page == currentPage) FontWeight.Bold else FontWeight.Normal,
                textDecoration = if (page == currentPage) TextDecoration.Underline else TextDecoration.None
            )
        }
    }
}


@Composable
fun HeaderBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "번호",
            color = Color.White,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )
        Text(
            text = "식품명",
            color = Color.White,
            modifier = Modifier.weight(3f).padding(start = 8.dp)
        )
        Text(
            text = "에너지(kcal)",
            color = Color.White,
            modifier = Modifier.weight(2f).padding(start = 8.dp)
        )
        Text(
            text = "비교함",
            color = Color.White,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )
    }
}

