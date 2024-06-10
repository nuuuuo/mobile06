package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DB.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodViewModelFactory(private val repository: FoodRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            return FoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class FoodViewModel (private val repository: FoodRepository) : ViewModel(){

    private var _foodList = MutableStateFlow<List<Food>>(emptyList())  //flow에는 값(value)속성이 있어서 저장 가능
    val foodList = _foodList.asStateFlow()

    fun searchFood(foodName:String) {
        viewModelScope.launch {
            repository.searchFood(foodName).collect{
                _foodList.value = it
            }
        }
    }
}