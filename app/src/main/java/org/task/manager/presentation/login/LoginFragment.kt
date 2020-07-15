package org.task.manager.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.data.network.model.response.AuthenticationState
import org.task.manager.databinding.FragmentLoginBinding
import org.task.manager.hide
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class LoginFragment : Fragment(), ViewElements {
    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberMeCheckBox: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.new_password)
        rememberMeCheckBox = view.findViewById(R.id.rememberMe)

        binding.loginButton.setOnClickListener {
            showProgress()
            viewModel.authenticate(usernameEditText.text.toString(),
                passwordEditText.text.toString(), rememberMeCheckBox.isActivated)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.refuseAuthentication()
            navController.popBackStack(R.id.main_fragment, false)
        }

        viewModel.authenticationResult.observe(viewLifecycleOwner, Observer { authenticationResult ->
            when (authenticationResult.state) {
                AuthenticationState.AUTHENTICATED -> navController.popBackStack()
                AuthenticationState.UNAUTHENTICATED -> navController.popBackStack()
                AuthenticationState.INVALID_AUTHENTICATION -> navController.popBackStack()
            }
            hideProgress()
        })
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

}
