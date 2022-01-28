package com.example.mohammad_jaha

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mohammad_jaha.adapter.SearchRVAdapter
import com.example.mohammad_jaha.data.Data
import com.example.mohammad_jaha.view_model.MainViewModel
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONArray
import java.net.URL

class SearchName : Fragment() {

    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var searchButton: Button
    private lateinit var searchEntry: EditText
    private lateinit var backButton: Button
    private lateinit var searchName: String
    private lateinit var searchDataList: ArrayList<Data>
    private lateinit var searchRV: RecyclerView
    private lateinit var adapter: SearchRVAdapter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_name, container, false)
        searchButton = view.findViewById(R.id.searchButton)
        searchEntry = view.findViewById(R.id.searchEntry)
        backButton = view.findViewById(R.id.backButton)
        searchRV = view.findViewById(R.id.searchRV)

        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please Wait:")
        progressDialog.setCancelable(false)

        searchDataList = arrayListOf()
        rvAdapter()

        searchButton.setOnClickListener {
            if (searchEntry.text.isNotBlank()) {
                progressDialog.show()
                searchName = searchEntry.text.toString().replace(" ", "&")
                fetchingData()
            } else {
                StyleableToast.makeText(
                    requireContext(),
                    "Please Enter Valid Value",
                    R.style.failToast
                ).show()
            }
            val keyboard: View? = requireActivity().currentFocus
            if (keyboard != null) {
                val inputMethodManager: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(keyboard.windowToken, 0)
            }
        }

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_searchName_to_mainPage)
        }
        return view
    }

    private fun rvAdapter() {
        adapter = SearchRVAdapter(searchDataList)
        searchRV.adapter = adapter
        searchRV.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickListener(object : SearchRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                mainViewModel.addInformation(searchDataList[position])
                StyleableToast.makeText(requireContext(), "Saved Successfully", R.style.addToast)
                    .show()
            }
        })
    }

    private fun fetchingData() {
        CoroutineScope(IO).launch {
            val data =
                withContext(Default) { getData() }

            if (data.isNotEmpty()) {
                parsingData(data)
            }
        }
    }

    private fun getData(): String {

        return try {
            URL("https://api.tvmaze.com/search/shows?q=$searchName").readText(Charsets.UTF_8)
        } catch (e: Exception) {
            "Unable to get Data: $e"
        }
    }

    private suspend fun parsingData(result: String) {
        withContext(Main) {
            Log.d("Error XXX", result)
            try {
                searchDataList.clear()
                val jsonArray = JSONArray(result)
                if (jsonArray.length() == 0)
                    StyleableToast.makeText(requireContext(), "No Result", R.style.updateToast)
                        .show()
                for (i in 0 until jsonArray.length()) {
                    val component = jsonArray.getJSONObject(i).getJSONObject("show")
                    val name = component.getString("name")
                    val language = component.getString("language")
                    val picture: String? = try {
                        component.getJSONObject("image").getString("original")
                    } catch (e: java.lang.Exception) {
                        null
                    }
                    val summary = Html.fromHtml(component.getString("summary")).toString()
                    searchDataList.add(Data(0, name, language, picture, summary))
                }
                adapter.update()
            } catch (e: Exception) {
                Log.d("Error XXX", "Error On Parsing Data $e")
            }
            progressDialog.dismiss()
        }
    }
}