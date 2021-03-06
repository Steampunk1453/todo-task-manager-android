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
import org.task.manager.databinding.FragmentResetFinishPasswordBinding
import org.task.manager.domain.model.state.AccountState
import org.task.manager.hide
import org.task.manager.presentation.shared.ValidatorService
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class PasswordResetFinishFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentResetFinishPasswordBinding
    private lateinit var navController: NavController
    private val viewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_finish_password, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tokenKey = arguments?.getString("key")

        binding.resetButton.setOnClickListener {
            val (isValid, error) = validatorService.isValidPassword(binding.newPassword.text.toString(),
                    binding.newPasswordConfirmation.text.toString())
            if (isValid) {
                viewModel.finishResetPassword(tokenKey.toString(), binding.newPassword.text.toString())
            } else {
                showMessage(error)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_main)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.finishResetPasswordState.observe(viewLifecycleOwner, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> handleChangePassword()
                AccountState.INVALID_UPDATE -> showMessage(R.string.changed_password_error)
                AccountState.UPDATING -> showMessage(R.string.updating_password)
                else ->  AccountState.INVALID_UPDATE
            }
        })
    }

    private fun handleChangePassword() {
        showMessage(R.string.changed_password)
        navController.navigate(R.id.fragment_login)
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