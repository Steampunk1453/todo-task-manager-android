package org.task.manager.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.task.manager.R
import org.task.manager.databinding.FragmentRegistrationBinding
import org.task.manager.presentation.login.LoginViewModel

class RegistrationFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val registrationViewModel: RegistrationViewModel by activityViewModels()
    private lateinit var binding: FragmentRegistrationBinding

    private lateinit var usernameEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var newPasswordConfirmationEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()

        usernameEditText = view.findViewById(R.id.username)
        newPasswordEditText = view.findViewById(R.id.new_password)
        newPasswordConfirmationEditText = view.findViewById(R.id.new_password_confirmation)

        // When the register button is clicked, collect the current values from
        // the two edit texts and pass to the ViewModel to complete registration.
       binding.registerButton.setOnClickListener {
            registrationViewModel.createAccountAndLogin(
                usernameEditText.text.toString(),
                newPasswordEditText.text.toString()
            )
        }

        // RegistrationViewModel updates the registrationState to
        // REGISTRATION_COMPLETED when ready, and for this example, the username
        // is accessed as a read-only property from RegistrationViewModel and is
        // used to directly authenticate with loginViewModel.
        registrationViewModel.registrationState.observe(
            viewLifecycleOwner, Observer { state ->
                if (state == RegistrationViewModel.RegistrationState.REGISTRATION_COMPLETED) {

                    // Here we authenticate with the token provided by the ViewModel
                    // then pop back to the profie_fragment, where the user authentication
                    // status will be tested and should be authenticated.
                    val authToken = registrationViewModel.authToken
                    loginViewModel.authenticate(authToken, "")
                    navController.popBackStack(R.id.main_fragment, false)
                }
            }
        )

        // If the user presses back, cancel the user registration and pop back
        // to the login fragment. Since this ViewModel is shared at the activity
        // scope, its state must be reset so that it is in the initial state if
        // the user comes back to register later.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            registrationViewModel.userCancelledRegistration()
            navController.popBackStack(R.id.main_fragment, false)
        }

    }
}