package com.example.earsna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.earsna.ui.account.AccountViewModel
import com.example.earsna.util.PreferenceHelper

class OnboardingActivity : AppCompatActivity() {

    val accountViewmodel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        accountViewmodel.a

        // preference helper is custom class to facilitate store and get preference values
        var preferenceHelper = PreferenceHelper.getInstance(this)
        var isUserRegisterd = preferenceHelper.getBoolean("REGISTERED" , false)

        if(isUserRegisterd)  findNavController(R.id.onboarding_nav_host_fragment).navigate(R.id.loginFragment)
        else findNavController(R.id.onboarding_nav_host_fragment).navigate(R.id.registrationFragment)
    }
}