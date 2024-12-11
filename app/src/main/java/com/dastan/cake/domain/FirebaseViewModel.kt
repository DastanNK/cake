package com.dastan.cake.domain

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(private val storage:FirebaseStorage): ViewModel(){
    private val _imageUri= MutableStateFlow<String?>(null)
    val imageUri : StateFlow<String?> = _imageUri
    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("image/$uuid")
        imageRef.putFile(uri).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener(onSuccess)
        }.addOnFailureListener {
            Log.d("viewModelFirebase", "Not properly uploaded to firebase")
        }
    }

    fun postImage(uri:Uri){
        viewModelScope.launch {
            uploadImage(uri){firebaseUri->
                _imageUri.value = firebaseUri.toString()
                Log.d("viewModelFirebase", firebaseUri.toString())
            }
        }

    }

}