package com.example.interpark.viewModels

import androidx.lifecycle.*
import com.example.interpark.auth.AuthManager
import kotlinx.coroutines.launch
import com.example.interpark.data.ReviewRepository
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.ReviewError
import com.example.interpark.data.types.ReviewRequestBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException


class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {
    private val _review = MutableLiveData<Review>()
    val review: LiveData<Review> get() = _review

    private val _reviewWriteStatus = MutableStateFlow<Result<Unit>?>(null)
    val reviewWriteStatus: StateFlow<Result<Unit>?> = _reviewWriteStatus

    private val _reviewWriteError = MutableStateFlow<ReviewError?>(null)
    val reviewWriteError: StateFlow<ReviewError?> = _reviewWriteError

    fun writeReview(performanceId: String, rating: Int, title: String, content: String){
        if(ratingError.value == true || titleError.value == true || contentError.value == true){
            if(rating == 0) _ratingError.value = true
            return
        }
        viewModelScope.launch{
            try{
                if(AuthManager.getUser() != null){
                    val response = repository.writeReview(performanceId, ReviewRequestBody(rating, title, content), AuthManager.getUser()!!)
                    if(response.isSuccessful){
                        _reviewWriteStatus.value = Result.success(Unit)
                    } else{
                        _reviewWriteError.value = when (response.code()) {
                            401 -> ReviewError.Unauthorized
                            in 500..599 -> ReviewError.ServerError
                            else -> ReviewError.Unknown(response.message())
                        }
                    }
                }
                else{
                    _reviewWriteError.value = ReviewError.Unauthorized
                }
            }
             catch (e: IOException) {
                _reviewWriteError.value = ReviewError.NetworkError
             } catch (e: Exception) {
                _reviewWriteError.value = ReviewError.Unknown(e.message)
             }
        }
    }

    fun resetStatus(){
        _reviewWriteStatus.value = null
        _reviewWriteError.value = null
    }

    private val _ratingError = MutableLiveData<Boolean>(false)
    val ratingError: LiveData<Boolean> get() = _ratingError
    fun ratingCheck(rating: Int) {
        _ratingError.value = rating == 0
    }

    private val _titleError = MutableLiveData<Boolean>(false)
    val titleError: LiveData<Boolean> get() = _titleError
    fun titleCheck(title: String) {
        _titleError.value = title == ""
    }

    private val _contentError = MutableLiveData<Boolean>(false)
    val contentError: LiveData<Boolean> get() = _contentError
    fun contentCheck(content: String) {
        _contentError.value = content == ""
    }

}