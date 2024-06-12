package com.example.sjsk.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sjsk.firestore.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.ceil

class SearchViewModel(private val repository: Repository) : ViewModel() {
    private val _data = MutableStateFlow<ApiResponse?>(null)
    val data: StateFlow<ApiResponse?> = _data

    private var currentPage = 1
    private var totalPages = 1
    private var currentFoodName = ""

    fun searchFood(foodName: String) {
        currentFoodName = foodName
        currentPage = 1
        fetchFoodData()
    }
    fun changePage(page: Int) {
        currentPage = page
        fetchFoodData()
    }

    fun getTotalPages(): Int {
        return totalPages
    }

    fun fetchFoodData() {
        viewModelScope.launch(Dispatchers.IO) {  // Change to Dispatchers.IO
            try {
                val response = repository.fetchFoodData(currentPage, currentFoodName).execute()
                if (response.isSuccessful) {
                    _data.value = response.body()
                    response.body()?.body?.totalCount?.let {
                        totalPages = ceil(it.toDouble() / 10).toInt()
                    }
                    Log.d("API_SUCCESS", "Data fetched successfully: ${response.body()}")
                } else {
                    _data.value = null
                    Log.e("API_ERROR", "Response not successful: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _data.value = null
                Log.e("API_EXCEPTION", "Error during API call: ${e.message}", e)
            }
        }
    }
}
