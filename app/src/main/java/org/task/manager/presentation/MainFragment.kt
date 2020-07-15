package org.task.manager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.task.manager.R
import org.task.manager.databinding.FragmentMainBinding
import org.task.manager.presentation.login.LoginViewModel

class MainFragment : Fragment() {
    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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
    }

}