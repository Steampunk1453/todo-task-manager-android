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
import kotlinx.android.synthetic.main.fragment_login.progressBar
import kotlinx.android.synthetic.main.fragment_reset_init_password.email
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentResetInitPasswordBinding
import org.task.manager.domain.model.state.AccountState
import org.task.manager.hide
import org.task.manager.presentation.shared.ValidatorService
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

private const val CHANGED_PASSWORD_MESSAGE = "Check your emails for details on how to reset your password"
private const val INCORRECT_PASSWORD_ERROR_MESSAGE = "Error resetting password"
private const val UPDATING_PASSWORD_MESSAGE = "Updating password"

class PasswordResetInitFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentResetInitPasswordBinding
    private lateinit var navController: NavController
    private val viewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener {
            val (isValid, error) = validatorService.isValidEmail(email.text.toString())
            if (isValid) {
                viewModel.startResetPassword(email.text.toString())
            } else {
                showMessage(error)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_home)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.startResetPasswordState.observe(viewLifecycleOwner, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> showMessage(CHANGED_PASSWORD_MESSAGE)
                AccountState.INVALID_UPDATE -> showMessage(INCORRECT_PASSWORD_ERROR_MESSAGE)
                AccountState.UPDATING -> showMessage(UPDATING_PASSWORD_MESSAGE)
            }
        })
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