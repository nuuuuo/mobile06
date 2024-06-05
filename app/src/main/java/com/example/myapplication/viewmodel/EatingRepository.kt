package com.example.myapplication.viewmodel

import com.example.myapplication.roomDB.Eating
import com.example.myapplication.roomDB.EatingDatabase

class EatingRepository(private val db: EatingDatabase) {
    val dao = db.getDao()

    suspend fun insertEating(eating: Eating) {
        dao.insertEating(eating)
    }

    suspend fun deleteEating(eating: Eating) {
        dao.deleteEating(eating)
    }

    suspend fun updateEating(eating: Eating) {
        dao.updateEating(eating)
    }

    fun getAllEating() = dao.getAllEating()

    suspend fun deleteAllEating() = dao.deleteAllEating()

}