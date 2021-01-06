package org.task.manager.presentation.account.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentResetInitPasswordBinding
import org.task.manager.domain.model.state.AccountState
import org.task.manager.hide
import org.task.manager.presentation.shared.ValidatorService
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class PasswordResetInitFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentResetInitPasswordBinding
    private lateinit var navController: NavController
    private val viewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_init_password, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.resetButton.setOnClickListener {
            val (isValid, error) = validatorService.isValidEmail(binding.email.text.toString())
            if (isValid) {
                viewModel.startResetPassword(binding.email.text.toString())
            } else {
                showMessage(error)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_login)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.startResetPasswordState.observe(viewLifecycleOwner, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> showMessage(R.string.changed_password_init)
                AccountState.INVALID_UPDATE -> showMessage(R.string.changed_password_init_error)
                else -> AccountState.UPDATING
            }
        })
    }

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        binding.progressBar.show()
    }

    override fun hideProgress() {
        binding.progressBar.hide()
    }

}