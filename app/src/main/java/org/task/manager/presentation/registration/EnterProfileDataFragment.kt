package org.task.manager.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.task.manager.R

class EnterProfileDataFragment : Fragment() {

    val registrationViewModel by activityViewModels<RegistrationViewModel>()

    private lateinit var fullnameEditText: EditText
    private lateinit var bioEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()

        fullnameEditText = view.findViewById(R.id.fullname)
        bioEditText = view.findViewById(R.id.bio)

        // When the next button is clicked, collect the current values from the
        // two edit texts and pass to the ViewModel to store.
        view.findViewById<Button>(R.id.next_button).setOnClickListener {
            val name = fullnameEditText.text.toString()
            val bio = bioEditText.text.toString()
            registrationViewModel.collectProfileData(name, bio)
        }

        // RegistrationViewModel will update the registrationState to
        // COLLECT_USER_PASSWORD when ready to move to the choose username and
        // password screen.
        registrationViewModel.registrationState.observe(
            viewLifecycleOwner, Observer { state ->
                if (state == RegistrationViewModel.RegistrationState.COLLECT_USER_PASSWORD) {
                    navController.navigate(R.id.move_to_choose_user_password)
                }
            })

        // If the user presses back, cancel the user registration and pop back
        // to the login fragment. Since this ViewModel is shared at the activity
        // scope, its state must be reset so that it will be in the initial
        // state if the user comes back to register later.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            registrationViewModel.userCancelledRegistration()
            navController.popBackStack(R.id.login_fragment, false)
        }
    }
}