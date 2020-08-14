package com.example.earsna.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.earsna.AuthState
import com.example.earsna.MainActivity
import com.example.earsna.R
import com.example.earsna.databinding.FragmentVerifyPhoneBinding
import com.example.earsna.util.PreferenceHelper
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit
import com.example.earsna.util.PreferenceHelper.set


class VerifyPhoneFragment : Fragment() {

    val accountViewmodel: AccountViewModel by activityViewModels()

    var code : String? = null

    // used to check wheather the user moved to this fragment from forgot password fragment or from registration fragment
    // because to reset password the user must verify the phone number first
    var forgotPassword = false
    var phoneNumber : String? = null

    lateinit var binding: FragmentVerifyPhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            forgotPassword = it.getBoolean("FORGOT_PASSWORD" , false)
            phoneNumber = it.getString("PHONE_NUMBER")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentVerifyPhoneBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        accountViewmodel.accountState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is AuthState.LoggedinState -> {
                    //update registration preference to true flag the user is registered
                    var preference = PreferenceHelper.getInstance(requireContext())
                    preference["REGISTERED"] = true

                    // user account created then save full user info to firestore db
                    saveUserFullInfo(state.user!!.uid)
                }
                is AuthState.LoggedOutState -> Toast.makeText(
                    requireContext(),
                    state.error ?: "error occured",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        if(forgotPassword) createVerficationRequest(phoneNumber!!)
        else accountViewmodel.user?.phone_number?.let { createVerficationRequest(it) }


        binding.verifyConfirmBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            var userSubmitedCode = binding.verifcationEditText.text.toString()
            if (code == userSubmitedCode) {
                // if navigation from is not from forgot password
                if(!forgotPassword){
                    //1 create user for authentication
                    accountViewmodel.user?.let {
                        accountViewmodel.createUser(it.email, it.password)
                    }
                } // else navigation from forgot password fragment ... move to reset password
                else{
                    findNavController().navigate(R.id.resetPasswordFragment)
                }

            }
            else{
                Toast.makeText(requireContext(), "Invlid input type", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun createVerficationRequest(phoneNumber : String) {
        Log.i("firebasephone", phoneNumber)
        var phoneAuthProvider = PhoneAuthProvider.getInstance()
        phoneAuthProvider.verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, requireActivity(),
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    binding.verifyConfirmBtn.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    code = p0.smsCode

                }
                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.i("firebasephone", "onVerificationFailed", p0)
                    Toast.makeText(requireContext(), p0.message, Toast.LENGTH_LONG).show()
                }
            })
    }


    fun saveUserFullInfo(userIdfromUserCreation: String) {
        accountViewmodel.user?.let {
            var userInfo = hashMapOf(
                "user_id" to userIdfromUserCreation,
                "first_name" to it.first_name,
                "last_name" to it.last_name,
                "phone_number" to it.phone_number ,
                "role" to it.role,
                "country" to it.country,
                "city" to it.city
            )
            var db = FirebaseFirestore.getInstance()
            db.collection("users").add(userInfo)
                .addOnSuccessListener { task ->
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), task.id, Toast.LENGTH_LONG).show()
                    var intent = Intent(requireContext() , MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { task ->
                    Toast.makeText(requireContext(), task.message, Toast.LENGTH_LONG).show()
                }
        }
    }
}