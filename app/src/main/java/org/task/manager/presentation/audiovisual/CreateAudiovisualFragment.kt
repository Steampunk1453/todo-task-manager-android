package org.task.manager.presentation.audiovisual

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_create_audiovisual.checkBox
import kotlinx.android.synthetic.main.fragment_create_audiovisual.save
import kotlinx.android.synthetic.main.fragment_create_audiovisual.titleText
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentCreateAudiovisualBinding
import org.task.manager.domain.model.Genre
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.shared.Constants.FALSE
import org.task.manager.shared.Constants.TRUE

class CreateAudiovisualFragment : DialogFragment() {
    private val audiovisualViewModel: AudiovisualViewModel by viewModel()
    private val dateService: DateService by inject()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentCreateAudiovisualBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_audiovisual,
            container,
            false
        )
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
        var genre = ""
        var platform = ""
        var platformUrl = ""

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        audiovisualViewModel.getTitles()
        audiovisualViewModel.getGenres()
        audiovisualViewModel.getPlatforms()

        binding.titleText.addTextChangedListener {
            isTitleFilled = it?.toString()?.isNotBlank() ?: false
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
            if (!startDatePicker.isAdded) startDatePicker.show(
                parentFragmentManager, "start_date_picker_tag"
            )
        }

        startDatePicker.addOnPositiveButtonClickListener {
            startDateMilliseconds = it
            binding.startDateText.setText(startDatePicker.headerText)
        }

        val deadlinePicker = builder.build()
        binding.deadlineText.setOnClickListener {
            if (!deadlinePicker.isAdded) deadlinePicker.show(
                parentFragmentManager,
                "deadline_picker_tag"
            )
        }

        deadlinePicker.addOnPositiveButtonClickListener {
            deadlineMilliseconds = it
            binding.deadlineText.setText(deadlinePicker.headerText)
        }

        audiovisualViewModel.titles.observe(viewLifecycleOwner, { list ->
            val titles = list as MutableList<Title>
            val titleNames = titles.map { it.name }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                titleNames
            )

            val suggestedTitlesDropdown: AutoCompleteTextView = binding.suggestedTitlesDropdown
            suggestedTitlesDropdown.setAdapter(adapter)

            suggestedTitlesDropdown.setOnItemClickListener { adapterView, view, pos, id ->
                val title = adapterView.getItemAtPosition(pos)
                binding.titleText.setText(title.toString())
            }
        })

        audiovisualViewModel.genres.observe(viewLifecycleOwner, { list ->
            val genres = list as MutableList<Genre>
            val genresNames = genres
                .filter { it.isLiterary != TRUE }
                .map { it.name }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                genresNames
            )

            val genresDropdown: AutoCompleteTextView = binding.genreDropdown
            genresDropdown.setAdapter(adapter)

            genresDropdown.setOnItemClickListener { adapterView, view, pos, id ->
                genre = adapterView.getItemAtPosition(pos).toString()
            }
        })

        audiovisualViewModel.platforms.observe(viewLifecycleOwner, { list ->
            val platforms = list as MutableList<Platform>
            val platformNames = platforms
                .map { it.name }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                platformNames
            )

            val platformsDropdown: AutoCompleteTextView = binding.platformDropdown
            platformsDropdown.setAdapter(adapter)

            platformsDropdown.setOnItemClickListener { adapterView, view, pos, id ->
                platform = adapterView.getItemAtPosition(pos).toString()
                platformUrl = platforms[pos].url
            }
        })

        audiovisualViewModel.audiovisual.observe(viewLifecycleOwner, Observer {
            dismiss()
            navController.navigate(R.id.fragment_audiovisual)
        })

        binding.save.setOnClickListener {

            if (binding.audiovisualId.tag != null) {
                audiovisualViewModel.updateAudiovisual(
                    binding.audiovisualId.tag.toString().toLong(),
                    titleText.text.toString(),
                    genre,
                    platform,
                    platformUrl,
                    startDateMilliseconds,
                    deadlineMilliseconds,
                    if (checkBox.isChecked) TRUE else FALSE,
                    binding.userId.tag.toString().toLong()
                )
            }
            else {
                audiovisualViewModel.createAudiovisual(
                    titleText.text.toString(),
                    genre,
                    platform,
                    platformUrl,
                    startDateMilliseconds,
                    deadlineMilliseconds,
                    if (checkBox.isChecked) TRUE else FALSE
                )
            }

        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        val action = arguments?.getString("action")
        if (action == "update") {
            sharedViewModel.audiovisual.observe(viewLifecycleOwner, Observer {
                binding.audiovisualId.tag = it.id
                binding.userId.tag = it.user?.id
                binding.titleText.setText(it.title)
                binding.genreDropdown.setText(it.genre)
                genre = it.genre
                binding.platformDropdown.setText(it.platform)
                platform = it.platform
                platformUrl = it.platformUrl
                binding.startDateText.setText(dateService.getFormattedDate(it.startDate))
                startDateMilliseconds =
                    dateService.convertDateToMilliseconds(binding.startDateText.text.toString())
                binding.deadlineText.setText(dateService.getFormattedDate(it.deadline))
                deadlineMilliseconds =
                    dateService.convertDateToMilliseconds(binding.deadlineText.text.toString())
                binding.checkBox.isChecked = it.check == 1
            })
        }
    }


    override fun onResume() {
        super.onResume()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    private fun isSaveEnabled(
        isTitleFilled: Boolean, isStartDateFilled: Boolean,
        isDeadlineFilled: Boolean
    ) {
        save.isEnabled = isTitleFilled && isStartDateFilled && isDeadlineFilled
    }

}