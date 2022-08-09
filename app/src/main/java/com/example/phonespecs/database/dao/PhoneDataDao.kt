package com.example.phonespecs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.phonespecs.entity.Phones

@Dao
interface PhoneDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPhonesData(phones: List<Phones>)

    @Query("SELECT * FROM tbl_phone")
    fun getAllPhonesData(): androidx.paging.DataSource.Factory<Int, Phones>

    @Query("SELECT COUNT(*) FROM tbl_phone")
    fun getTotalDataCount(): Int
}