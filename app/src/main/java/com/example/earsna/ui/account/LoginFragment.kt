package com.example.earsna.ui.account

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.earsna.AuthState
import com.example.earsna.MainActivity
import com.example.earsna.R
import com.example.earsna.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    val viewModel: AccountViewModel by activityViewModels()

    lateinit var binding : LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.accountState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is AuthState.LoggedinState -> {
                    binding.progressBar2.visibility = View.GONE
                    var intent = Intent(requireContext() , MainActivity::class.java)
                    startActivity(intent)
                }
                is AuthState.LoggedOutState -> Toast.makeText(requireContext(), state.error ?: "error occured", Toast.LENGTH_LONG).show()
            }
        })

        binding.loginBtn.setOnClickListener {
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditTextview.text.toString()
            binding.progressBar2.visibility = View.VISIBLE
            viewModel.authenticateWithEmail(email , password)
        }

        binding.forgotPasswordBtn.setOnClickListener {
            findNavController().navigate(R.id.phoneNumberFragment)
        }

        binding.goToSignupTextview.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }



}