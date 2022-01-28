package com.example.mohammad_jaha

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class MainPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        view.findViewById<Button>(R.id.searchButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainPage_to_searchName)
        }

        view.findViewById<Button>(R.id.databaseButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainPage_to_informationPage)
        }

        return view
    }

}