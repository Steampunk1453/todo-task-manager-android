package org.task.manager.presentation.audiovisual

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_create_audiovisual.checkBox
import kotlinx.android.synthetic.main.fragment_create_audiovisual.genreText
import kotlinx.android.synthetic.main.fragment_create_audiovisual.platformText
import kotlinx.android.synthetic.main.fragment_create_audiovisual.save
import kotlinx.android.synthetic.main.fragment_create_audiovisual.titleText
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentCreateAudiovisualBinding

class CreateAudiovisualFragment : DialogFragment() {

    private val viewModel: AudiovisualViewModel by viewModel()
    private lateinit var binding: FragmentCreateAudiovisualBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_audiovisual, container, false)
        navController = findNavController()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var isTitleFilled = false
        var isStartDateFilled = false
        var isDeadlineFilled = false
        var startDateMilliseconds = 1L
        var deadlineMilliseconds = 1L

        binding.titleText.addTextChangedListener { isTitleFilled = it?.toString()?.isNotBlank() ?: false
            isSaveEnabled(isTitleFilled, isStartDateFilled, isDeadlineFilled)
        }

        binding.startDateText.addTextChangedListener {
            isStartDateFilled = it?.toString()?.isNotBlank() ?: false
            isSaveEnabled(isTitleFilled, isStartDateFilled, isDeadlineFilled)
        }

        binding.deadlineText.addTextChangedListener {
            isDeadlineFilled = it?.toString()?.isNotBlank() ?: false
            isSaveEnabled(isTitleFilled, isStartDateFilled, isDeadlineFilled)

        }

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setSelection(Calendar.getInstance().timeInMillis);
        builder.setTitleText("Select a date")

        val startDatePicker = builder.build()
        binding.startDateText.setOnClickListener {
            if(!startDatePicker.isAdded) startDatePicker.show(parentFragmentManager, "start_date_picker_tag")
        }

        startDatePicker.addOnPositiveButtonClickListener {
            startDateMilliseconds = it
            binding.startDateText.setText(startDatePicker.headerText)
        }

        val deadlinePicker = builder.build()
        binding.deadlineText.setOnClickListener {
            if(!deadlinePicker.isAdded) deadlinePicker.show(parentFragmentManager, "deadline_picker_tag")
        }

        deadlinePicker.addOnPositiveButtonClickListener {
            deadlineMilliseconds = it
            binding.deadlineText.setText(deadlinePicker.headerText)
        }

        binding.save.setOnClickListener {
            viewModel.createAudiovisual(titleText.text.toString(), genreText.text.toString(),
                platformText.text.toString(), startDateMilliseconds,
                deadlineMilliseconds, if (checkBox.isChecked) 1 else 0)
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        viewModel.audiovisual.observe(viewLifecycleOwner, Observer {
            dismiss()
            navController.navigate(R.id.fragment_audiovisual)
        })
    }

    override fun onResume() {
        super.onResume()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun isSaveEnabled(isTitleFilled: Boolean, isStartDateFilled: Boolean,
                              isDeadlineFilled: Boolean) {
        save.isEnabled = isTitleFilled && isStartDateFilled && isDeadlineFilled
    }

    companion object {
        const val TAG = "CreateAudiovisualFragment"

        fun newInstance(): CreateAudiovisualFragment {
            return CreateAudiovisualFragment()
        }
    }
}