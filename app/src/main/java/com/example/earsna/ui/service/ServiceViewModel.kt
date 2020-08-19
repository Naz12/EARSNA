package com.example.earsna.ui.service

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.example.earsna.model.Service
import com.google.firebase.firestore.FirebaseFirestore
import java.security.AccessController.getContext

class ServiceViewModel : ViewModel() {
    fun createService(service: Service) {
    val db = FirebaseFirestore.getInstance()
        db.collection("services").add(service)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }
    // TODO: Implement the ViewModel
}