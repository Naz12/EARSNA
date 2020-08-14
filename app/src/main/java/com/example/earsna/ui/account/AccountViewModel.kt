package com.example.earsna.ui.account

import android.content.Intent
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.earsna.AuthState
import com.example.earsna.MainActivity
import com.example.earsna.UserManager
import com.example.earsna.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class AccountViewModel constructor(var savedStateHandle: SavedStateHandle) : ViewModel() {

    var a = ""

    var accountState = MutableLiveData<AuthState>()

    //hold user info to transfer from registration fragment to phone verify fragment because registration takes place after phone verifcation
    var user : User? = null

    fun createUser(email: String, password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var state = AuthState.LoggedinState(user = firebaseAuth.currentUser!!)
                accountState.value = state
            } else accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
        }
    }

    fun authenticateWithEmail(email: String, password: String){
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                var state = AuthState.LoggedinState(user = firebaseAuth.currentUser!!)
                accountState.value = state
            }
            else  accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
        }
    }


    fun resetPassword(password : String){
        var firebaseAuth = FirebaseAuth.getInstance()
         firebaseAuth.currentUser?.let {
            it.updatePassword(password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    var user = firebaseAuth.currentUser
                    accountState.value = AuthState.LoggedinState(user = user!!)
                }
                else{
                    accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
                }
            }
        }
    }
}