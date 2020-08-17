package com.example.earsna.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.earsna.R
import com.example.earsna.ui.payment.PaymentViewmodel

class PartenerReportFragment : Fragment() {

    private val viewmodel : ReportViewmodel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_partener_report, container, false)
        return root
    }
}