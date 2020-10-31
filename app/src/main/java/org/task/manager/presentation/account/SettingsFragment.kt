package org.task.manager.presentation.account

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
import kotlinx.android.synthetic.main.fragment_registration.email
import kotlinx.android.synthetic.main.fragment_settings.firstName
import kotlinx.android.synthetic.main.fragment_settings.lastName
import kotlinx.android.synthetic.main.fragment_settings.username
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentSettingsBinding
import org.task.manager.domain.model.state.AccountState
import org.task.manager.hide
import org.task.manager.presentation.shared.ValidatorService
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

class SettingsFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var navController: NavController
    private val viewModel: SettingsViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAccount()

        showProgress()
        viewModel.user.observe(viewLifecycleOwner, {
            binding.username.text = it.username
            binding.firstName.setText(it.firstName)
            binding.lastName.setText(it.lastName)
            binding.email.setText(it.email)
        })
        hideProgress()

        binding.saveButton.setOnClickListener {
            val (isValid, error) = validatorService.isValidEmail(email.text.toString())
            if (isValid) {
                showProgress()
                viewModel.updateAccount(
                    username.text.toString(),
                    firstName.text.toString(),
                    lastName.text.toString(),
                    email.text.toString(),
                )
            } else {
                showMessage(error)
            }
        }

        viewModel.updateAccountState.observe(viewLifecycleOwner, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> manageUpdateComplete()
                AccountState.INVALID_UPDATE -> showMessage("Error saving settings, try again")
                AccountState.UPDATING -> showMessage("Updating")
            }
            hideProgress()
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_home)
        }
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

    private fun manageUpdateComplete() {
        showMessage("Settings saved")
        navController.navigate(R.id.fragment_home)
    }

}