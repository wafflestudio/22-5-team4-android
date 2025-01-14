package com.example.interpark.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.data.Performance
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.types.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformanceDetailViewModel(private val repository: PerformanceRepository) : ViewModel() {

    private val _performanceDetail = MutableLiveData<Performance>()
    val performanceDetail: LiveData<Performance> get() = _performanceDetail

    private val _performanceReviews = MutableLiveData<List<Review>>()
    val performanceReviews: LiveData<List<Review>> get() = _performanceReviews

    fun fetchPerformanceDetail(id: String?) {
        viewModelScope.launch {
            val performance = withContext(Dispatchers.IO) {
                repository.fetchPerformanceById(id) // 서버에서 ID로 공연 정보 가져오기
            }
            _performanceDetail.postValue(performance)
            Log.d("PerformanceDetailVM", "Fetched performance: $performance")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchPerformanceReviews(id: String){
        viewModelScope.launch {
            val reviews = withContext(Dispatchers.IO) {
                repository.fetchPerformanceReview(id) // 서버에서 ID로 공연 정보 가져오기
            }
            Log.d("viewmodel:", reviews.toString())
            _performanceReviews.postValue(reviews)
        }
    }

}
