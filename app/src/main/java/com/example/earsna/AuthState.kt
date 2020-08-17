package com.example.earsna

import com.example.earsna.model.User
import com.google.firebase.auth.FirebaseUser

sealed class AuthState(var user : FirebaseUser? = null , var error : String? = null , var fullUserInfo : User? = null) {
    class LoggedinState(user : FirebaseUser? = null , fullUserInfo: User? = null) : AuthState(user , null , fullUserInfo)
    class LoggedOutState(error: String?) : AuthState(null ,  error)
}