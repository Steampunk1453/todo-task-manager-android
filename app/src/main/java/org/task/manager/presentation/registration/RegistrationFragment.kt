package org.task.manager.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.username
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentRegistrationBinding
import org.task.manager.hide
import org.task.manager.presentation.login.LoginViewModel
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class RegistrationFragment : Fragment(), ViewElements {

    private val registrationViewModel: RegistrationViewModel by viewModel()
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var  navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


       binding.registerButton.setOnClickListener {
           if(validations(email.text.toString(), newPassword.text.toString(),
                   newPasswordConfirmation.text.toString())) {
               showProgress()
               registrationViewModel.createAccount(
                   username.text.toString(),
                   email.text.toString(),
                   newPassword.text.toString()
               )
           }
        }

        registrationViewModel.registrationState.observe(
            viewLifecycleOwner, Observer { state ->
                if (state == RegistrationViewModel.RegistrationState.REGISTRATION_COMPLETED) {
                    showMessage("Successful register")
                    navController.popBackStack(R.id.login_fragment, false)
                }
                hideProgress()
            }
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            registrationViewModel.userCancelledRegistration()
            navController.popBackStack(R.id.main_fragment, false)
        }

    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBarReg.show()
    }

    override fun hideProgress() {
        progressBarReg.hide()
    }

    private fun validations(email: String, password: String, passwordConfirmation: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showMessage("The email isn't correct")
            return false
        }
        else if (password.length < 4 || passwordConfirmation.length < 4) {
            showMessage("The password is required to be at least 4 characters")
            return false
        }
        else if (password != passwordConfirmation) {
            showMessage("The password and its confirmation do not match!")
            return false
        }
        return true
    }
}