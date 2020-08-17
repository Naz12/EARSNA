package com.example.earsna.ui.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.earsna.AuthState
import com.example.earsna.model.User
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class AccountViewModel constructor(var savedStateHandle: SavedStateHandle) : ViewModel() {

    var a = ""
    var accountState = MutableLiveData<AuthState>()

    //hold user info to transfer from registration fragment to phone verify fragment because registration takes place after phone verifcation
    var user : User? = null

    fun createUser(email: String, password: String , phoneCred : PhoneAuthCredential) {
        var firebaseAuth = FirebaseAuth.getInstance()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseAuth.currentUser!!.updatePhoneNumber(phoneCred).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        var state = AuthState.LoggedinState(user = firebaseAuth.currentUser!!)
                        accountState.value = state
                    }
                    else accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
                }
            } else accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
        }
    }



    fun authenticateWithEmail(email: String, password: String , userInfo : User){
        var firebaseAuth = FirebaseAuth.getInstance()
        PhoneAuthProvider.PHONE_SIGN_IN_METHOD
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                var state = AuthState.LoggedinState(user = firebaseAuth.currentUser!! , fullUserInfo = userInfo)
                accountState.value = state
            }
            else  accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
        }
    }


    fun getUserByPhoneNumberAndAuthenticate(phoneNumber : String , password: String){
        var db = FirebaseFirestore.getInstance()
        db.collection("users").whereEqualTo("phone_number" ,phoneNumber)
            .whereEqualTo("password" , password)
            .get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                task.result?.let {
                    if(!it.isEmpty){
                        var fullUserInfo = it.toObjects(User::class.java).first()
                        authenticateWithEmail(fullUserInfo.email!! , password , fullUserInfo)
//                        var state = AuthState.LoggedinState(fullUserInfo = fullUserInfo)
//                        accountState.value = state
                        return@addOnCompleteListener
                    }
                }
                accountState.value = AuthState.LoggedOutState(error = "No user found")
            }
            else  accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
        }
    }
    fun getFullUserInfo(user : FirebaseUser){
        var db = FirebaseFirestore.getInstance()
         db.collection("users").whereEqualTo("user_id" , user.uid).get().addOnCompleteListener { task ->
             if(task.isSuccessful){
                 task.result?.let {
                    var fullUserInfo = it.toObjects(User::class.java).first()
                     var state = AuthState.LoggedinState(user = user , fullUserInfo = fullUserInfo)
                     accountState.value = state
                     return@addOnCompleteListener
                 }
                 accountState.value = AuthState.LoggedOutState(error = "No user found")
             }
             else  accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
         }
    }


    fun resetPassword(phoneNumber : String , password : String , phoneAuthCre : PhoneAuthCredential){
        var auth =  FirebaseAuth.getInstance()
        auth.signInWithCredential(phoneAuthCre).addOnCompleteListener { task ->
            if(task.isSuccessful){
                auth.currentUser!!.updatePassword(password).addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        var db = FirebaseFirestore.getInstance()
                        db.collection("users").document(auth.currentUser!!.uid).update("password" , password)
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful){
                                   Log.i("updatedresult" , task.result.toString())
                                    var state = AuthState.LoggedinState()
                                    accountState.value = state
                                }
                                else  accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
                            }
                    }
                    else  accountState.value = AuthState.LoggedOutState(error = task.exception?.message)
                }
            }
        }
    }
}