package com.example.mohammad_jaha.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mohammad_jaha.data.Data

@Dao
interface InformationDao {
    @Query("SELECT * FROM MyList")
    fun getAllInformation(): LiveData<List<Data>>

    @Insert
    fun addInformation(data: Data)

    @Delete
    fun deleteInformation(data: Data)
}