package org.task.manager.presentation.user.login

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_login.progressBar
import kotlinx.android.synthetic.main.fragment_login.rememberMe
import kotlinx.android.synthetic.main.fragment_login.username
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentLoginBinding
import org.task.manager.domain.model.state.AuthenticationState
import org.task.manager.hide
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class LoginFragment : Fragment(), ViewElements {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var  navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        navController = findNavController()
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            loginViewModel.authenticate(username.text.toString(), password.text.toString(), rememberMe.isActivated)
        }

        binding.resetButton.setOnClickListener {
            navController.navigate(R.id.fragment_reset_init_password)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            loginViewModel.refuseAuthentication()
            navController.navigate(R.id.fragment_main)
        }

        observeViewModel()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    private fun observeViewModel() {
        showProgress()
        loginViewModel.authenticationResult.observe(viewLifecycleOwner, { authenticationResult ->
            when (authenticationResult.state) {
                AuthenticationState.AUTHENTICATED -> authenticatedUser(authenticationResult.message)
                AuthenticationState.INVALID_AUTHENTICATION -> showMessage(authenticationResult.message)
                AuthenticationState.UNAUTHENTICATED -> navController.popBackStack()
            }
        })
        hideProgress()
    }

    private fun authenticatedUser(message: String) {
        showMessage(message)
        sharedViewModel.setUserName(username.text.toString())
        binding.root.hideKeyboard()
        navController.navigate(R.id.fragment_home)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}
