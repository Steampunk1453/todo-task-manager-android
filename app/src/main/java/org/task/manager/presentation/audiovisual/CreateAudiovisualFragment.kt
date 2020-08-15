package org.task.manager.presentation.audiovisual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.task.manager.R

class CreateAudiovisualFragment : DialogFragment() {

//    private val vm: TasksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_audiovisual, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        taskText.addTextChangedListener {
//            createButton.isEnabled = it?.toString()?.isNotBlank() ?: false
//        }
//
//        createButton.setOnClickListener {
//            vm.create(taskText.text.toString())
//            dismiss()
//        }
//
//        cancelButton.setOnClickListener {
//            dismiss()
//        }
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