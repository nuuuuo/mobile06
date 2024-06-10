package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class PersonViewModelFactory(private val repository: PersonRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
            return PersonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PersonViewModel (private val repository: PersonRepository) : ViewModel() {

    private var _personList =
        MutableStateFlow<List<Person>>(emptyList())  //flow에는 값(value)속성이 있어서 저장 가능
    val personList = _personList.asStateFlow()

    fun getPerson(personID: String) {
        repository.getPerson(personID)
    }

    fun updatePersonIntake(intake:Map<LocalDate, Nutrient>) {
        viewModelScope.launch {
            repository.updatePersonIntake(intake)
        }
    }
}