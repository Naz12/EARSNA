package com.example.earsna

import com.google.firebase.auth.FirebaseUser

sealed class AuthState(var user : FirebaseUser? = null , var error : String? = null) {
    class LoggedinState(user : FirebaseUser) : AuthState(user)
    class LoggedOutState(error: String?) : AuthState(null ,  error)
}