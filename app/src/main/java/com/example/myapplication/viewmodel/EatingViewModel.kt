package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.roomDB.Eating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EatingViewModelFactory(private val repository: EatingRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EatingViewModel::class.java)) {
            return EatingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class EatingViewModel(private val repository: EatingRepository): ViewModel() {
    private var _todayEating = MutableStateFlow<List<Eating>>(emptyList())
    val todayEating = _todayEating.asStateFlow()

    fun saveEating(eating: Eating) {
        viewModelScope.launch {
            repository.insertEating(eating)
            getAllEating()
        }
    }

    fun deleteEating(eating: Eating) {
        viewModelScope.launch {
            repository.deleteEating(eating)
            getAllEating()
        }
    }

    fun updateEating(eating: Eating) {
        viewModelScope.launch {
            repository.updateEating(eating)
            getAllEating()
        }
    }

    fun getAllEating() {
        viewModelScope.launch {
            repository.getAllEating().collect {
                _todayEating.value = it
            }
        }
    }

    fun deleteAllEating() {
        viewModelScope.launch {
            repository.deleteAllEating()
        }
    }
}