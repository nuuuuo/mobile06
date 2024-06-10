package com.example.ku

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Nav.BottomNavigationBar
import com.example.myapplication.Nav.NavigationHost
import com.example.myapplication.roomDB.EatingDatabase
import com.example.myapplication.viewmodel.EatingRepository
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.EatingViewModelFactory

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val navController = rememberNavController()
    val eatingdb = EatingDatabase.getEatingDatabase(context)
    val eatingViewModel: EatingViewModel = viewModel(factory = EatingViewModelFactory(
        EatingRepository(eatingdb)
    )
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {contentPadding ->
        Column (
            modifier = Modifier.padding(contentPadding)
        ){
            NavigationHost(navController = navController, eatingViewModel = eatingViewModel, productViewModel = viewModel())
        }
    }
}