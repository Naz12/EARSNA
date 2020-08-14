package com.example.earsna.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.earsna.R
import kotlinx.android.synthetic.main.fragment_phone_number.*

class PhoneNumberFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        countryCodePicker.registerCarrierNumberEditText(phone_number_edit_text)
        verify_btn.setOnClickListener {
            var fullPhoneNumber = countryCodePicker.fullNumberWithPlus
            var bundle = bundleOf("PHONE_NUMBER" to fullPhoneNumber , "FORGOT_PASSWORD" to true)
            findNavController().navigate(R.id.verifyPhoneFragment , bundle)
        }
    }


}