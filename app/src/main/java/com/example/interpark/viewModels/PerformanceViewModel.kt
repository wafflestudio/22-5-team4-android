package com.example.interpark.viewModels

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.R
import com.example.interpark.data.CategoryItem
import com.example.interpark.data.Performance
import com.example.interpark.data.PerformanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PerformanceViewModel(private val repository: PerformanceRepository) : ViewModel() {
    private val _performance = MutableLiveData<Performance>()
    val performance: MutableLiveData<Performance> get() = _performance

    private val _performanceList = MutableLiveData<List<Performance>>(listOf())
    val performanceList: LiveData<List<Performance>> get() = _performanceList

    fun fetchPerformanceList(category: String?, title: String?){
        viewModelScope.launch {
            val performances = withContext(Dispatchers.IO){
                repository.fetchPerformances(category, title)
            }
            _performanceList.postValue(performances)
            Log.d("viewmodel", performances.toString())
        }
    }

}

