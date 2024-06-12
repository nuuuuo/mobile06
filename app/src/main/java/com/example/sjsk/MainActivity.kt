package com.example.sjsk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search Food") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.searchFood(searchText) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
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
            data.body?.items?.forEach { items ->
                items.item?.forEach { item ->
                    Text(text = "식품코드: ${item.FOOD_CD}, 식품명: ${item.FOOD_NM_KR}")
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