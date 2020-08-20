package org.task.manager.presentation.audiovisual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_create_audiovisual.cancel
import kotlinx.android.synthetic.main.fragment_create_audiovisual.deadlineText
import kotlinx.android.synthetic.main.fragment_create_audiovisual.save
import kotlinx.android.synthetic.main.fragment_create_audiovisual.startDateText
import kotlinx.android.synthetic.main.fragment_create_audiovisual.titleText
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R

class CreateAudiovisualFragment : DialogFragment() {

    private val viewModel: AudiovisualViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_audiovisual, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var isSaveEnabled: Boolean

        titleText.addTextChangedListener {
            isSaveEnabled = it?.toString()?.isNotBlank() ?: false
        }

        startDateText.addTextChangedListener {
            isSaveEnabled = it?.toString()?.isNotBlank() ?: false
        }

        deadlineText.addTextChangedListener {
            isSaveEnabled = it?.toString()?.isNotBlank() ?: false
            save.isEnabled = isSaveEnabled
        }

        save.setOnClickListener {
//            viewModel.create(taskText.text.toString())
            dismiss()
        }

        cancel.setOnClickListener {
            dismiss()
        }

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select a date")
        val picker = builder.build()

        startDateText.setOnClickListener {
            if(!picker.isAdded) picker.show(parentFragmentManager, "date_picker_tag")
        }

        picker.addOnPositiveButtonClickListener {
            startDateText.setText(picker.headerText)
        }

    }

    override fun onResume() {
        super.onResume()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        const val TAG = "CreateAudiovisualFragment"

        fun newInstance(): CreateAudiovisualFragment {
            return CreateAudiovisualFragment()
        }
    }
}