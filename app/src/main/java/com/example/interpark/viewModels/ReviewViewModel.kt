package com.example.interpark.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.interpark.auth.AuthManager
import kotlinx.coroutines.launch
import com.example.interpark.data.ReviewRepository
import com.example.interpark.data.types.Comment
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.ReviewError
import com.example.interpark.data.types.ReviewRequestBody
import com.google.rpc.context.AttributeContext.Auth
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

    private val _reviewList = MutableLiveData<List<Review>?>()
    val reviewList: LiveData<List<Review>?> = _reviewList

    private val _reviewReadError = MutableStateFlow<ReviewError?>(null)
    val reviewReadError: StateFlow<ReviewError?> = _reviewReadError

    fun readReview(performanceId: String?){
        if(performanceId == null) return
        viewModelScope.launch {
            try {
                val response = repository.readReview(performanceId)
                if(response.isSuccessful){
                    _reviewList.value = response.body()
                }
            }
            catch (e: IOException) {
                _reviewReadError.value = ReviewError.NetworkError
            } catch (e: Exception) {
                _reviewReadError.value = ReviewError.Unknown(e.message)
            }
        }
    }

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

    private val _comment = MutableLiveData<List<Comment>>()
    val comment: LiveData<List<Comment>> get() = _comment


    fun readComment(reviewId: String){
        viewModelScope.launch {
            val response = repository.readComment(reviewId)
            if(response.isSuccessful){
                _comment.value = response.body()
            }
        }
    }

    fun writeComment(context: Context, reviewId: String, content: String){
        if(content == ""){
            return
        }
        viewModelScope.launch {
            if(AuthManager.getUser() != null){
                val response = repository.writeComment(reviewId, content)
                if(response.isSuccessful){
                    val updatedList = _comment.value.orEmpty().toMutableList()
                    updatedList.add(0, response.body()!!) // 맨 앞에 추가
                    _comment.value = updatedList
                }
            }
            else{
                Toast.makeText(context, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
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