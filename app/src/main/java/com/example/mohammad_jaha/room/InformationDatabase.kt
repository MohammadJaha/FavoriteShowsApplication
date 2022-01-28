package com.example.mohammad_jaha.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mohammad_jaha.data.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class InformationDatabase : RoomDatabase() {

    abstract fun informationDao(): InformationDao

    companion object {
        @Volatile
        var instance: InformationDatabase? = null

        fun getInstance(context: Context): InformationDatabase {
            if (instance != null)
                return instance!!
            synchronized(this) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    InformationDatabase::class.java,
                    "MyDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}