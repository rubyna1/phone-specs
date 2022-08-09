package com.example.phonespecs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.phonespecs.database.dao.PhoneDataDao
import com.example.phonespecs.entity.Phones

@Database(entities = [(Phones::class)], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun phoneDataDao(): PhoneDataDao
}