package com.example.earsna

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserManager @Inject constructor(var firebaseAuth: FirebaseAuth) {

   suspend fun createUser(email : String , password : String){
          firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener { task ->
          if(task.isSuccessful) {
               AuthState.LoggedinState(user = firebaseAuth.currentUser!!)
          }
//          else(task.isCanceled)  AuthState.LoggedOutState(error = task.exception?.message)

        }
    }

    fun verifyPhone(){

    }
}