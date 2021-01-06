package org.task.manager.presentation

import android.net.Uri
import android.os.Bundle
import android.view.Gravity.RIGHT
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.BuildConfig
import org.task.manager.R
import org.task.manager.domain.model.state.RegistrationState
import org.task.manager.hide
import org.task.manager.presentation.user.login.LoginViewModel
import org.task.manager.presentation.user.login.LogoutState
import org.task.manager.presentation.user.registration.RegistrationViewModel
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show
import timber.log.Timber
import timber.log.Timber.DebugTree

private const val ACTIVATE_ACCOUNT_PATH = "account"
private const val RESET_ACCOUNT_PATH = "reset"
private const val PARAMETER_NAME = "key"

class MainActivity : AppCompatActivity(), ViewElements {

    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController
    private val registrationViewModel: RegistrationViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)

        handleBottomNavigationView()

        handleNavigationController()

        handleIntentData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> onBackPressed().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    private fun handleBottomNavigationView() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_home -> showBottomNav()
                R.id.fragment_audiovisual -> showBottomNav()
                R.id.fragment_book -> showBottomNav()
                R.id.fragment_settings -> showBottomNav()
                R.id.fragment_password -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun handleNavigationController() {
        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_home -> {
                    navController.navigate(R.id.fragment_home)
                    true
                }
                R.id.fragment_audiovisual -> {
                    navController.navigate(R.id.fragment_audiovisual)
                    true
                }
                R.id.fragment_book -> {
                    navController.navigate(R.id.fragment_book)
                    true
                }
                R.id.menu_account -> {
                    showUserAccountPopup(navView)
                    true
                }
                else -> false
            }
        }
    }

    private fun handleIntentData() {
        val data: Uri? = this.intent.data

        if (data != null && data.isHierarchical) {
            val uri = this.intent.dataString
            val activateKey = this.intent.dataString?.toHttpUrlOrNull()?.queryParameter(PARAMETER_NAME)
            val secondPath = this.intent.dataString?.toHttpUrlOrNull()?.pathSegments?.get(1)

            Timber.i("Deep link clicked: $uri")
            Timber.i("Activate Key: $activateKey")
            Timber.i("Second Path: $secondPath")

            if (secondPath == ACTIVATE_ACCOUNT_PATH) activateUserAccount(activateKey)
            else if (secondPath == RESET_ACCOUNT_PATH) resetUserPassword(activateKey)
        }
    }

    private fun activateUserAccount(activateKey: String?) {
        activateKey?.let {
            registrationViewModel.activateAccount(it)
        }

        registrationViewModel.registrationState.observe(this, { registrationResult ->
            if (registrationResult == RegistrationState.ACTIVATION_COMPLETED) {
                Toast.makeText(this, R.string.user_account_activate, Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.fragment_login)
            }
            else if (registrationResult == RegistrationState.INVALID_ACTIVATION) {
                Toast.makeText(this, R.string.user_account_error, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetUserPassword(key: String?) {
        val bundle = bundleOf("key" to key)
        navController.navigate(R.id.fragment_reset_finish_password, bundle)
    }

    private fun showUserAccountPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_account)
        popup.gravity = RIGHT

        popup.show()

        handleNavigationAccountPopup(popup)
    }

    private fun handleNavigationAccountPopup(popup: PopupMenu) {
        popup.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.settings -> {
                    navController.navigate(R.id.fragment_settings)
                    true
                }
                R.id.password -> {
                    navController.navigate(R.id.fragment_password)
                    true
                }
                R.id.signOut -> {
                    singOutUser()
                    navController.navigate(R.id.fragment_main)
                    true
                }
                else -> false
            }
        }
    }

    private fun singOutUser() {
        loginViewModel.singOut()

        loginViewModel.logoutState.observe(this, { state ->
            if (LogoutState.LOGOUT_COMPLETE == state) {
                showMessage(R.string.successful_logout)
            } else {
                showMessage(R.string.error_logout)
            }
        })
    }

    private fun showBottomNav() {
        navView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        navView.visibility = View.GONE
    }

}