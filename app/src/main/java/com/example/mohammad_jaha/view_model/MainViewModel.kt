package com.example.mohammad_jaha.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mohammad_jaha.data.Data
import com.example.mohammad_jaha.room.InformationDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val connection = InformationDatabase.getInstance(application).informationDao()

    fun getAllInformation(): LiveData<List<Data>> {
        return connection.getAllInformation()
    }

    fun addInformation(data: Data) {
        CoroutineScope(IO).launch {
            connection.addInformation(data)
        }
    }

    fun deleteInformation(data: Data) {
        CoroutineScope(IO).launch {
            connection.deleteInformation(data)
        }
    }
}