package org.task.manager.presentation.login

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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.domain.model.AuthenticationState
import org.task.manager.databinding.FragmentLoginBinding
import org.task.manager.hide
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class LoginFragment : Fragment(), ViewElements {
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            showProgress()
            viewModel.authenticate(username.text.toString(), password.text.toString(), rememberMe.isActivated)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.refuseAuthentication()
            navController.popBackStack(R.id.main_fragment, false)
        }

        viewModel.authenticationResult.observe(viewLifecycleOwner, Observer { authenticationResult ->
            when (authenticationResult.state) {
                AuthenticationState.AUTHENTICATED -> authenticatedUser(authenticationResult.message)
                AuthenticationState.INVALID_AUTHENTICATION -> showMessage(authenticationResult.message)
                AuthenticationState.UNAUTHENTICATED ->  navController.popBackStack()
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

    private fun authenticatedUser(message: String) {
        showMessage(message)
        navController.popBackStack()
    }

}
