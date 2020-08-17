package com.example.earsna.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.earsna.R
import com.example.earsna.databinding.FragmentRegistrationBinding
import com.example.earsna.model.User


class RegistrationFragment : Fragment() {


    lateinit var binding: FragmentRegistrationBinding

    val accountViewmodel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.countryCodePicker.registerCarrierNumberEditText(binding.registrationPhoneNumberEditText)
        binding.registerBtn.setOnClickListener {
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            var firstName = binding.firstNameEditText.text.toString()
            var lastName = binding.lastNameEditText.text.toString()
            var phoneNumber = binding.countryCodePicker.fullNumberWithPlus
            var country = binding.countryEditText.text.toString()
            var city = binding.cityEditText.text.toString()

            var userInfo = User( firstName, lastName, email, password, phoneNumber , 0 , country , city)
            accountViewmodel.user = userInfo
            findNavController().navigate(R.id.verifyPhoneFragment)
        }
    }






}