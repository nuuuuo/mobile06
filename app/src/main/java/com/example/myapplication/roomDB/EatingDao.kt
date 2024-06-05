package com.example.myapplication.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.roomDB.Eating
import kotlinx.coroutines.flow.Flow

@Dao
interface EatingDao {

    @Insert
    suspend fun insertEating(eating: Eating)

    @Update
    suspend fun updateEating(eating: Eating)

    @Delete
    suspend fun deleteEating(eating: Eating)

    @Query("SELECT * FROM Eating")
    fun getAllEating(): Flow<List<Eating>>

    @Query("DELETE FROM Eating")
    suspend fun deleteAllEating()
}