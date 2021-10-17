package org.task.manager.presentation.audiovisual

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentCreateAudiovisualBinding
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.shared.Constants.DATE_PICKER_TITLE_TEXT
import org.task.manager.shared.Constants.FALSE
import org.task.manager.shared.Constants.TRUE

private const val EVENT_TITLE_PREFIX = "Watch "
private const val AUDIOVISUAL_EVENT = "Video to watch"
private const val SIZE_LIMIT = 9
private const val RADIO_BUTTON_SELECTED_ID = "radio_button_tv_show"
private const val RADIO_BUTTON_MOVIE_TEXT = "Movie"
private const val RADIO_BUTTON_TV_SHOW_TEXT = "TV Show"

class CreateAudiovisualFragment : DialogFragment() {

    private lateinit var binding: FragmentCreateAudiovisualBinding
    private lateinit var navController: NavController
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var radioButton: RadioButton
    private val audiovisualViewModel: AudiovisualViewModel by viewModel()
    private val dateService: DateService by inject()
    private lateinit var genre: String
    private lateinit var platform: String
    private lateinit var platformUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_audiovisual, container,
            false
        )
        navController = findNavController()
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var isTitleFilled = false
        var isStartDateFilled = false
        var isDeadlineFilled = false
        var startDateMilliseconds = 1L
        var deadlineMilliseconds = 1L

        val action = arguments?.getString("action")
        if (action == "update") {
            sharedViewModel.audiovisual.observe(viewLifecycleOwner, {
                binding.audiovisualId.tag = it.id
                binding.userId.tag = it.user?.id
                binding.titleText.setText(it.title)
                binding.audiovisualGenreDropdown.setText(it.genre)
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
                binding.checkBox.isChecked = it.check == TRUE
            })
        }

        observeViewModel()

        val radioButtonText = getRadioButtonSelectedText(view)
        populateSuggestedTitles(radioButtonText)
        handleSuggestedTitles()

        audiovisualViewModel.getGenres()
        handleGenres()

        audiovisualViewModel.getPlatforms()
        handlePlatforms()

        val startDatePicker = getDatePickerBuilder().build()
        startDatePicker.addOnPositiveButtonClickListener {
            startDateMilliseconds = it + dateService.addHours(19)
            binding.startDateText.setText(startDatePicker.headerText)
        }

        val deadlinePicker = getDatePickerBuilder().build()
        deadlinePicker.addOnPositiveButtonClickListener {
            deadlineMilliseconds = it + dateService.addHours(19)
            binding.deadlineText.setText(deadlinePicker.headerText)
        }

        binding.titleText.addTextChangedListener {
            isTitleFilled = it?.toString()?.isNotBlank() ?: false
            isSaveEnabled(isTitleFilled, isStartDateFilled, isDeadlineFilled)
            reloadDropdowns(isTitleFilled)
        }

        if (!binding.titleText.hasFocus()) {
            binding.root.hideKeyboard()
        }

        binding.startDateText.addTextChangedListener {
            isStartDateFilled = it?.toString()?.isNotBlank() ?: false
            isSaveEnabled(isTitleFilled, isStartDateFilled, isDeadlineFilled)
        }

        binding.deadlineText.addTextChangedListener {
            isDeadlineFilled = it?.toString()?.isNotBlank() ?: false
            isSaveEnabled(isTitleFilled, isStartDateFilled, isDeadlineFilled)
        }

        binding.startDateText.setOnClickListener {
            if (!startDatePicker.isAdded) startDatePicker.show(
                parentFragmentManager, "start_date_picker_tag"
            )
        }

        binding.deadlineText.setOnClickListener {
            if (!deadlinePicker.isAdded) deadlinePicker.show(
                parentFragmentManager, "deadline_picker_tag"
            )
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            radioButton = view.findViewById(checkedId)
            val selectedRadioButtonId = resources.getResourceEntryName(radioButton.id)
            val radioText = getRadioButtonText(selectedRadioButtonId)
            populateSuggestedTitles(radioText)
        }

        binding.save.setOnClickListener {

            if (binding.audiovisualId.tag != null) {
                audiovisualViewModel.updateAudiovisual(
                    AudiovisualDto(
                        binding.audiovisualId.tag.toString().toLong(),
                        binding.titleText.text.toString(),
                        genre,
                        platform,
                        platformUrl,
                        dateService.convertToInstant(startDateMilliseconds),
                        dateService.convertToInstant(deadlineMilliseconds),
                        if (binding.checkBox.isChecked) TRUE else FALSE,
                        binding.userId.tag.toString().toLong()
                    )
                )
            } else {
                audiovisualViewModel.createAudiovisual(
                    AudiovisualDto(
                        title = binding.titleText.text.toString(),
                        genre = genre,
                        platform = platform,
                        platformUrl = platformUrl,
                        startDate = dateService.convertToInstant(startDateMilliseconds),
                        deadline = dateService.convertToInstant(deadlineMilliseconds),
                        check = if (binding.checkBox.isChecked) TRUE else FALSE,
                    )
                )
                audiovisualViewModel.createCalendarEvent(
                    startDateMilliseconds,
                    EVENT_TITLE_PREFIX + binding.titleText.text.toString(),
                    AUDIOVISUAL_EVENT
                )
            }

        }

        binding.cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() {
        audiovisualViewModel.calendarEvents.observe(viewLifecycleOwner, { intent ->
            startActivity(intent)
        })

        audiovisualViewModel.audiovisual.observe(viewLifecycleOwner, {
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

    private fun getRadioButtonSelectedText(view: View): String {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        radioButton = view.findViewById(selectedId)
        val selectedRadioButtonId = resources.getResourceEntryName(radioButton.id)
        return getRadioButtonText(selectedRadioButtonId)
    }

    private fun populateSuggestedTitles(radioText: String) {
        val type = Type.fromString(radioText).name
        val textHintId =
            if (type == Type.TV_SHOW.name) R.string.suggested_tv_shows else R.string.suggested_movies
        binding.suggestedTitles.setHint(textHintId)

        audiovisualViewModel.getTitles(type)
    }

    private fun handleSuggestedTitles() {
        audiovisualViewModel.titles.observe(viewLifecycleOwner, { list ->
            val titles = list
                ?.sortedBy { it.rank }
                ?.filter { it.platform != null }
                ?.take(SIZE_LIMIT)

            val titleNames = titles?.map { it.title }

            val suggestedTitlesDropdown = titleNames?.let { buildSuggestedTitlesDropdown(it) }
            suggestedTitlesDropdown?.let { addTitlesItemSelectEvent(it, titles) }
        })
    }

    private fun buildSuggestedTitlesDropdown(titles: List<String?>): AutoCompleteTextView {
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, titles)

        val suggestedTitlesDropdown = binding.suggestedTitlesDropdown
        suggestedTitlesDropdown.setAdapter(adapter)

        return suggestedTitlesDropdown
    }

    private fun addTitlesItemSelectEvent(
        suggestedTitlesDropdown: AutoCompleteTextView,
        titles: List<Title>
    ) {
        suggestedTitlesDropdown.setOnItemClickListener { adapterView, _, pos, _ ->
            val titleName = adapterView.getItemAtPosition(pos)
            val selectedGenre = titles[pos].genres?.split(",")?.get(0).toString()
            val selectedPlatform = titles[pos].platform.toString()
            binding.titleText.setText(titleName.toString())
            binding.audiovisualGenreDropdown.setText(selectedGenre)
            genre = selectedGenre
            binding.platformDropdown.setText(selectedPlatform)
            platform = selectedPlatform
            platformUrl = titles[pos].website.toString()
            binding.root.hideKeyboard()
        }
    }

    private fun handleGenres() {
        audiovisualViewModel.genres.observe(viewLifecycleOwner, { list ->
            val genresNames = list
                .filter { it.isLiterary != TRUE }
                .take(SIZE_LIMIT)
                .map { it.name }

            val genresDropdown = buildGenresDropdown(genresNames)
            addGenresItemSelectEvent(genresDropdown)
        })
    }

    private fun buildGenresDropdown(genresNames: List<String>): AutoCompleteTextView {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            genresNames
        )

        val genresDropdown = binding.audiovisualGenreDropdown
        genresDropdown.setAdapter(adapter)

        return genresDropdown
    }

    private fun addGenresItemSelectEvent(genresDropdown: AutoCompleteTextView) {
        genresDropdown.setOnItemClickListener { adapterView, _, pos, _ ->
            genre = adapterView.getItemAtPosition(pos).toString()
            binding.root.hideKeyboard()
        }
    }

    private fun handlePlatforms() {
        audiovisualViewModel.platforms.observe(viewLifecycleOwner, { list ->
            val platformNames = list
                .take(SIZE_LIMIT)
                .map { it.name }

            val platformsDropdown = buildPlatformsDropdown(platformNames)
            addPlatformsItemSelectEvent(platformsDropdown, list)
        })
    }

    private fun buildPlatformsDropdown(platformNames: List<String>): AutoCompleteTextView {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            platformNames
        )

        val platformsDropdown = binding.platformDropdown
        platformsDropdown.setAdapter(adapter)

        return platformsDropdown
    }

    private fun addPlatformsItemSelectEvent(
        platformsDropdown: AutoCompleteTextView,
        platforms: List<Platform>
    ) {
        platformsDropdown.setOnItemClickListener { adapterView, _, pos, _ ->
            platform = adapterView.getItemAtPosition(pos).toString()
            platformUrl = platforms[pos].url
            binding.root.hideKeyboard()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDatePickerBuilder(): MaterialDatePicker.Builder<Long> {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setSelection(Calendar.getInstance().timeInMillis)
        builder.setTitleText(DATE_PICKER_TITLE_TEXT)
        return builder
    }

    private fun isSaveEnabled(
        isTitleFilled: Boolean, isStartDateFilled: Boolean,
        isDeadlineFilled: Boolean
    ) {
        binding.save.isEnabled = isTitleFilled && isStartDateFilled && isDeadlineFilled
    }

    private fun reloadDropdowns(isTitleFilled: Boolean) {
        if (!isTitleFilled) {
            handleGenres()
            handlePlatforms()
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getRadioButtonText(selectedRadioButtonId: String?) =
        if (selectedRadioButtonId == RADIO_BUTTON_SELECTED_ID) RADIO_BUTTON_TV_SHOW_TEXT else RADIO_BUTTON_MOVIE_TEXT

}