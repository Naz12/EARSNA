package com.example.earsna.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.earsna.R

class PartenerPaymentFragment : Fragment() {

    private val viewmodel : PaymentViewmodel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_partener_payment, container, false)
        return root
    }
}