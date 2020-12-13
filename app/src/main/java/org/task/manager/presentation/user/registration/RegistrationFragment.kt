package org.task.manager.presentation.user.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_registration.email
import kotlinx.android.synthetic.main.fragment_registration.newPassword
import kotlinx.android.synthetic.main.fragment_registration.newPasswordConfirmation
import kotlinx.android.synthetic.main.fragment_registration.progressBarReg
import kotlinx.android.synthetic.main.fragment_registration.username
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentRegistrationBinding
import org.task.manager.domain.model.state.RegistrationState
import org.task.manager.hide
import org.task.manager.presentation.shared.ValidatorService
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

private const val SUCCESSFUL_REGISTRATION_MESSAGE = "Registration saved! Please check your email for confirmation"

class RegistrationFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var navController: NavController
    private val viewModel: RegistrationViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registerButton.setOnClickListener {
            val (isValid, error) = validate(
                username.text.toString(),
                email.text.toString(),
                newPassword.text.toString(),
                newPasswordConfirmation.text.toString()
            )
            if (isValid) {
                viewModel.createAccount(
                    username.text.toString(),
                    email.text.toString(),
                    newPassword.text.toString()
                )
            } else {
                showMessage(error)
            }
            binding.root.hideKeyboard()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.userCancelledRegistration()
            navController.navigate(R.id.fragment_main)
        }

        observeViewModel()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progressBarReg.show()
    }

    override fun hideProgress() {
        progressBarReg.hide()
    }

    private fun observeViewModel() {
        showProgress()
        viewModel.registrationState.observe(
            viewLifecycleOwner, { state ->
                if (state == RegistrationState.REGISTRATION_COMPLETED) {
                    showMessage(SUCCESSFUL_REGISTRATION_MESSAGE)
                    navController.navigate(R.id.fragment_main)
                }
            }
        )
        hideProgress()
    }


    private fun validate(username: String, email: String, password: String, passwordConfirmation: String): Pair<Boolean, String> {
        val (isValidUsername, usernameErrorMessage) = validatorService.isValidUsername(username)
        if (!isValidUsername) return Pair(isValidUsername, usernameErrorMessage)

        val (isValidEmail, emailErrorMessage) = validatorService.isValidEmail(email)
        if (!isValidEmail) return Pair(isValidEmail, emailErrorMessage)

        val (isValidPassword, passwordErrorMessage) = validatorService.isValidPassword(password, passwordConfirmation)
        if (!isValidPassword) return Pair(isValidPassword, passwordErrorMessage)

        return Pair(true, "")
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}