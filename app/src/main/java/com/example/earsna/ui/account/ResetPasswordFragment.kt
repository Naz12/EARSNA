package com.example.earsna.ui.account

import android.content.Intent
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
import com.example.earsna.util.PreferenceHelper
import com.example.earsna.util.PreferenceHelper.set
import kotlinx.android.synthetic.main.fragment_reset_password.*


class ResetPasswordFragment : Fragment() {

    val accountViewmodel: AccountViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        accountViewmodel.accountState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is AuthState.LoggedinState -> {
                   // user password reseted
                    var intent = Intent(requireContext() , MainActivity::class.java)
                    startActivity(intent)
                }
                is AuthState.LoggedOutState -> Toast.makeText(requireContext(), state.error ?: "error occured resetting password", Toast.LENGTH_LONG).show()
            }
        })


        password_reset_btn.setOnClickListener {
            var newPassword = new_password_edit_text.text.toString()
            var confirmPassword = confirm_password_edit_text.text.toString()
            if(newPassword == confirmPassword){
                accountViewmodel.resetPassword(newPassword)
            }
        }
    }

}