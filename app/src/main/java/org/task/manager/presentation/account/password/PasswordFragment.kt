package org.task.manager.presentation.account.password

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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentPasswordBinding
import org.task.manager.domain.model.state.AccountState
import org.task.manager.hide
import org.task.manager.presentation.shared.ValidatorService
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class PasswordFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentPasswordBinding
    private lateinit var navController: NavController
    private val viewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener {
            val (isValid, error) = validatorService.isValidPassword(binding.newPassword.text.toString(),
                    binding.newPasswordConfirmation.text.toString())
            if (isValid) {
                viewModel.updatePassword(
                        binding.currentPassword.text.toString(),
                        binding.newPassword.text.toString()
                )
            } else {
                showMessage(error)
            }
            binding.root.hideKeyboard()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_home)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.updatePasswordState.observe(viewLifecycleOwner, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> handleUpdateComplete()
                AccountState.INVALID_UPDATE -> showMessage(R.string.incorrect_password)
                AccountState.UPDATING -> showMessage(R.string.updating_password)
                else ->  AccountState.INVALID_UPDATE
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

    private fun handleUpdateComplete() {
        showMessage(R.string.password_changed)
        navController.navigate(R.id.fragment_home)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}