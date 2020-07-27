package org.task.manager.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.progressBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentMainBinding
import org.task.manager.hide
import org.task.manager.presentation.MainFragmentDirections
import org.task.manager.presentation.login.LoginViewModel
import org.task.manager.presentation.login.LogoutState
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class HomeFragment : Fragment(), ViewElements {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.registerButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToRegistrationFragment2()
            findNavController().navigate(action)
        }

        binding.logoutButton.setOnClickListener {
            loginViewModel.singOut()
        }

        loginViewModel.logoutState.observe(
            viewLifecycleOwner, Observer { state ->
                if (LogoutState.LOGOUT_COMPLETE == state) {
                    showMessage("Successful logout")
                } else {
                    showMessage("Invalid logout, try again")
                }
            }
        )
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

}