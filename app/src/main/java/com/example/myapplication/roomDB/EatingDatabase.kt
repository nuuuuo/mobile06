package com.example.myapplication.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Eating::class],
    version = 1,
    exportSchema = false
)
abstract class EatingDatabase : RoomDatabase(){
    abstract fun getDao(): EatingDao

    companion object {
        private var database: EatingDatabase? = null
        fun getEatingDatabase(context: android.content.Context): EatingDatabase {
            return database ?: androidx.room.Room.databaseBuilder(
                    context,
                    EatingDatabase::class.java,
                    "eatingdb"
                ).build()
                    .also {
                        database = it
                    }
        }
    }
}