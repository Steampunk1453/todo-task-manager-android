package org.task.manager.presentation.book

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
import kotlinx.android.synthetic.main.fragment_create_book.authorText
import kotlinx.android.synthetic.main.fragment_create_book.checkBox
import kotlinx.android.synthetic.main.fragment_create_book.save
import kotlinx.android.synthetic.main.fragment_create_book.titleText
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentCreateBookBinding
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.shared.Constants.DATE_PICKER_TITLE_TEXT
import org.task.manager.shared.Constants.FALSE
import org.task.manager.shared.Constants.TRUE

private const val EVENT_TITLE_PREFIX = "Read "
private const val BOOK_EVENT = "Book to read"

class CreateBookFragment : DialogFragment() {

    private lateinit var binding: FragmentCreateBookBinding
    private lateinit var navController: NavController
    private lateinit var sharedViewModel: SharedViewModel
    private val bookViewModel: BookViewModel by viewModel()
    private val dateService: DateService by inject()
    private lateinit var genre: String
    private lateinit var editorial: String
    private lateinit var editorialUrl: String
    private lateinit var bookshop: String
    private lateinit var bookshopUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_book,
            container,
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
            sharedViewModel.book.observe(viewLifecycleOwner, {
                binding.bookId.tag = it.id
                binding.userId.tag = it.user?.id
                binding.titleText.setText(it.title)
                binding.authorText.setText(it.author)
                binding.genreDropdown.setText(it.genre)
                genre = it.genre
                binding.editorialDropdown.setText(it.editorial)
                editorial = it.editorial
                editorialUrl = it.editorialUrl
                binding.bookshopDropdown.setText(it.bookshop)
                bookshop = it.bookshop
                bookshopUrl = it.bookshopUrl
                binding.startDateText.setText(dateService.getFormattedDate(it.startDate))
                startDateMilliseconds =
                    dateService.convertDateToMilliseconds(binding.startDateText.text.toString())
                binding.deadlineText.setText(dateService.getFormattedDate(it.deadline))
                deadlineMilliseconds =
                    dateService.convertDateToMilliseconds(binding.deadlineText.text.toString())
                binding.checkBox.isChecked = it.check == 1
            })
        }

        observeViewModel()

        bookViewModel.getGenres()
        handleGenres()

        bookViewModel.getEditorials()
        handleEditorials()

        bookViewModel.getBookshops()
        handleBookshops()

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
                parentFragmentManager,
                "deadline_picker_tag"
            )
        }

        binding.save.setOnClickListener {

            if (binding.bookId.tag != null) {
                bookViewModel.updateBook(
                    binding.bookId.tag.toString().toLong(),
                    titleText.text.toString(),
                    authorText.text.toString(),
                    genre,
                    editorial,
                    editorialUrl,
                    bookshop,
                    bookshopUrl,
                    startDateMilliseconds,
                    deadlineMilliseconds,
                    if (checkBox.isChecked) TRUE else FALSE,
                    binding.userId.tag.toString().toLong()
                )
            } else {
                bookViewModel.createBook(
                    titleText.text.toString(),
                    authorText.text.toString(),
                    genre,
                    editorial,
                    editorialUrl,
                    bookshop,
                    bookshopUrl,
                    startDateMilliseconds,
                    deadlineMilliseconds,
                    if (checkBox.isChecked) TRUE else FALSE
                )
                bookViewModel.createCalendarEvent(
                    startDateMilliseconds,
                    EVENT_TITLE_PREFIX + titleText.text.toString(),
                    BOOK_EVENT
                )
            }

        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

    }

    private fun observeViewModel() {
        bookViewModel.calendarEvents.observe(viewLifecycleOwner, { intent ->
            startActivity(intent)
        })

        bookViewModel.book.observe(viewLifecycleOwner, Observer {
            dismiss()
            navController.navigate(R.id.fragment_book)
        })
    }

    override fun onResume() {
        super.onResume()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun handleGenres() {
        bookViewModel.genres.observe(viewLifecycleOwner, { list ->
            val genresNames = list
                .filter { it.isLiterary != FALSE }
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

        val genresDropdown = binding.genreDropdown
        genresDropdown.setAdapter(adapter)

        return genresDropdown
    }

    private fun addGenresItemSelectEvent(genresDropdown: AutoCompleteTextView) {
        genresDropdown.setOnItemClickListener { adapterView, _, pos, _ ->
            genre = adapterView.getItemAtPosition(pos).toString()
        }
    }

    private fun handleEditorials() {
        bookViewModel.editorials.observe(viewLifecycleOwner, { list ->
            val editorialNames = list
                .map { it.name }

            val editorialsDropdown = buildEditorialsDropdown(editorialNames)
            addEditorialsItemSelectEvent(editorialsDropdown, list)
        })
    }

    private fun buildEditorialsDropdown(editorialNames: List<String>): AutoCompleteTextView {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            editorialNames
        )

        val editorialsDropdown = binding.editorialDropdown
        editorialsDropdown.setAdapter(adapter)

        return editorialsDropdown
    }

    private fun addEditorialsItemSelectEvent(
        editorialsDropdown: AutoCompleteTextView,
        editorials: List<Editorial>
    ) {
        editorialsDropdown.setOnItemClickListener { adapterView, _, pos, _ ->
            editorial = adapterView.getItemAtPosition(pos).toString()
            editorialUrl = editorials[pos].url
        }
    }

    private fun handleBookshops() {
        bookViewModel.bookshops.observe(viewLifecycleOwner, { list ->
            val bookshopNames = list
                .map { it.name }

            val bookshopDropdown = buildBookshopsDropdown(bookshopNames)
            addBookshopsItemSelectEvent(bookshopDropdown, list)
        })
    }

    private fun buildBookshopsDropdown(bookshopNames: List<String>): AutoCompleteTextView {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            bookshopNames
        )

        val bookshopDropdown = binding.bookshopDropdown
        bookshopDropdown.setAdapter(adapter)

        return bookshopDropdown
    }

    private fun addBookshopsItemSelectEvent(
        bookshopDropdown: AutoCompleteTextView,
        bookshops: List<Bookshop>
    ) {
        bookshopDropdown.setOnItemClickListener { adapterView, _, pos, _ ->
            bookshop = adapterView.getItemAtPosition(pos).toString()
            bookshopUrl = bookshops[pos].url
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDatePickerBuilder(): MaterialDatePicker.Builder<Long> {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setSelection(Calendar.getInstance().timeInMillis);
        builder.setTitleText(DATE_PICKER_TITLE_TEXT)
        return builder
    }

    private fun isSaveEnabled(
        isTitleFilled: Boolean, isStartDateFilled: Boolean,
        isDeadlineFilled: Boolean
    ) {
        save.isEnabled = isTitleFilled && isStartDateFilled && isDeadlineFilled
    }

}