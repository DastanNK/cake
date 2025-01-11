package com.dastan.cake.data

import android.util.Log
import com.dastan.cake.data.api.post.RetrofitResponse
import com.dastan.cake.data.model.CakeOrderSubmission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CakeApiRepository {
    fun postToApi(cakeOrderSubmission: CakeOrderSubmission){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = RetrofitResponse.instance.createPost(cakeOrderSubmission)
                if (response.isSuccessful) {
                    Result.success(Unit)
                    Log.d("postToApi","yess")
                } else {
                    Log.d("postToApi", "nooo")
                }
            }catch (e:Exception){
                Log.d("postToApi", e.toString())
            }
        }
    }
}