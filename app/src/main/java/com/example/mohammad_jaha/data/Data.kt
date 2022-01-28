package com.example.mohammad_jaha.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyList")
data class Data(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val name: String,
    val language: String,
    val picture: String? = null,
    val summary: String
)
