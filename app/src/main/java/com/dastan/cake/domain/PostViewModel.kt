package com.dastan.cake.domain

import androidx.lifecycle.ViewModel
import com.dastan.cake.data.CakeApiRepository
import com.dastan.cake.data.model.CakeOrderSubmission

class PostViewModel(private val cakeApiRepository: CakeApiRepository):ViewModel() {
    fun postApi(cakeOrderSubmission: CakeOrderSubmission){
        cakeApiRepository.postToApi(cakeOrderSubmission)
    }
}