package com.example.mohammad_jaha

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mohammad_jaha.adapter.InformationRVAdapter
import com.example.mohammad_jaha.adapter.SearchRVAdapter
import com.example.mohammad_jaha.data.Data
import com.example.mohammad_jaha.view_model.MainViewModel
import io.github.muddz.styleabletoast.StyleableToast

class InformationPage : Fragment() {

    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var backButton: Button
    private lateinit var dataList: ArrayList<Data>
    private lateinit var mainRV: RecyclerView
    private lateinit var adapter: InformationRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_information_page, container, false)

        backButton = view.findViewById(R.id.backButton)
        mainRV = view.findViewById(R.id.mainRV)

        dataList = arrayListOf()

        rvAdapter()

        mainViewModel.getAllInformation().observe(viewLifecycleOwner) {
            dataList.clear()
            dataList.addAll(it)
            adapter.updateRVMain()
        }

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_informationPage_to_mainPage)
        }

        return view
    }

    private fun rvAdapter() {
        adapter = InformationRVAdapter(dataList)
        mainRV.adapter = adapter
        mainRV.layoutManager = LinearLayoutManager(context)

        adapter.setOnClickListener(object : InformationRVAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(context)
                    .setTitle("Details")
                    .setMessage(dataList[position].summary)
                    .setCancelable(false)
                    .setNegativeButton("OK") { out, _ -> out.cancel() }
                    .show()

            }

            override fun onDeleteClick(position: Int) {
                AlertDialog.Builder(context)
                    .setTitle("Are You Sure You Want To Delete")
                    .setMessage(dataList[position].name)
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        mainViewModel.deleteInformation(dataList[position])
                        StyleableToast.makeText(
                            requireContext(),
                            "Deleted Successfully",
                            R.style.deleteToast
                        )
                            .show()
                    }
                    .setNegativeButton("Cancel") { out, _ -> out.cancel() }
                    .show()
            }

        })
    }
}