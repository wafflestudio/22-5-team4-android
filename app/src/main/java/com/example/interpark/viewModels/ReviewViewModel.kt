package com.example.interpark.viewModels

import androidx.lifecycle.*
import com.example.interpark.auth.AuthManager
import kotlinx.coroutines.launch
import com.example.interpark.data.ReviewRepository
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.ReviewRequestBody


class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {
    private val _review = MutableLiveData<Review>()
    val review: LiveData<Review> get() = _review

    fun writeReview(performanceId: String, rating: Int, title: String, content: String){
        viewModelScope.launch{
            if(AuthManager.getUser() != null){
                repository.writeReview(performanceId, ReviewRequestBody(rating, title, content), AuthManager.getUser()!!)
            }
        }
    }
}