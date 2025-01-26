package com.example.interpark.fragments.SearchPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.SearchHistoryAdapter

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchFragment : Fragment() {

    private val PREF_NAME = "search_preferences"
    private val KEY_SEARCH_HISTORY = "search_history"
    private lateinit var sharedPreferences: SharedPreferences

    private val searchHistory = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // SharedPreferences 초기화
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // 검색 기록 로드
        loadSearchHistory()

        // RecyclerView 초기화
        setupRecyclerView(view)

        // 검색 버튼 클릭 리스너
        val buttonSearch = view.findViewById<ImageButton>(R.id.buttonSearch)
        val editTextSearch = view.findViewById<EditText>(R.id.editTextSearch)
        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                addSearchHistory(query)
                saveSearchHistory()
                navigateToSearchResults(query)
            } else {
                Toast.makeText(requireContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSearchHistory)
        val adapter = SearchHistoryAdapter(searchHistory) { query ->
            // 검색 기록 클릭 시 EditText에 값 설정
            view.findViewById<EditText>(R.id.editTextSearch).setText(query)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addSearchHistory(query: String) {
        if (!searchHistory.contains(query)) {
            searchHistory.add(0, query) // 중복 방지를 위해 리스트 상단에 추가
        }
    }

    private fun saveSearchHistory() {
        // 검색 기록을 JSON 문자열로 변환 후 SharedPreferences에 저장
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(searchHistory)
        editor.putString(KEY_SEARCH_HISTORY, json)
        editor.apply()
    }

    private fun loadSearchHistory() {
        // SharedPreferences에서 JSON 문자열을 읽어 리스트로 복원
        val json = sharedPreferences.getString(KEY_SEARCH_HISTORY, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<String>>() {}.type
            val savedHistory: MutableList<String> = Gson().fromJson(json, type)
            searchHistory.clear()
            searchHistory.addAll(savedHistory)
        }
    }

    private fun navigateToSearchResults(query: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(query)
        findNavController().navigate(action)
    }
}
