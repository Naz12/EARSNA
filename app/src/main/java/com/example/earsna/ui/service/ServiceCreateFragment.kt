package com.example.earsna.ui.service

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.earsna.R
import com.example.earsna.model.Service
import kotlinx.android.synthetic.main._fragment.*
import kotlinx.android.synthetic.main.service_create_fragment.*

class ServiceCreateFragment : Fragment() {

    val  serviceViewModel: ServiceViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.service_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnCreateService.setOnClickListener(View.OnClickListener {
            var service: Service = Service("",
                name_edit_text.text.toString(),
                price_edit_text.text.toString().toDouble(), "","", true, address_edit_text.text.toString(),
                null, null, null, city_edit_text.toString(), country_edit_text.toString()

            );
            serviceViewModel.createService(service);
        })
    }
}