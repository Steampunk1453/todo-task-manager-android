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
import org.task.manager.domain.model.Genre
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.shared.Constants.FALSE
import org.task.manager.shared.Constants.TRUE


class CreateBookFragment : DialogFragment() {
    private val bookViewModel: BookViewModel by viewModel()
    private lateinit var sharedViewModel: SharedViewModel
    private val dateService: DateService by inject()
    private lateinit var binding: FragmentCreateBookBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_book,
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
        var editorial = ""
        var editorialUrl = ""
        var bookshop = ""
        var bookshopUrl = ""


        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

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

        bookViewModel.genres.observe(viewLifecycleOwner, { list ->
            val genres = list as MutableList<Genre>
            val genresNames = genres
                .filter { it.isLiterary != FALSE }
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

        bookViewModel.editorials.observe(viewLifecycleOwner, { list ->
            val editorials = list as MutableList<Editorial>
            val editorialNames = editorials
                .map { it.name }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                editorialNames
            )

            val editorialsDropdown: AutoCompleteTextView = binding.editorialDropdown
            editorialsDropdown.setAdapter(adapter)

            editorialsDropdown.setOnItemClickListener { adapterView, view, pos, id ->
                editorial = adapterView.getItemAtPosition(pos).toString()
                editorialUrl = editorials[pos].url
            }
        })

        bookViewModel.bookshops.observe(viewLifecycleOwner, Observer { list ->
            val bookshops = list as MutableList<Bookshop>
            val bookshopNames = bookshops.map { it.name }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                bookshopNames
            )

            val bookshopDropdown: AutoCompleteTextView = binding.bookshopDropdown
            bookshopDropdown.setAdapter(adapter)

            bookshopDropdown.setOnItemClickListener { adapterView, view, pos, id ->
                bookshop = adapterView.getItemAtPosition(pos).toString()
                bookshopUrl = bookshops[pos].url
            }
        })


        bookViewModel.book.observe(viewLifecycleOwner, Observer {
            dismiss()
            navController.navigate(R.id.fragment_book)
        })

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
            }
            else {
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
            }

        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        val action = arguments?.getString("action")
        if (action == "update") {
            sharedViewModel.book.observe(viewLifecycleOwner, Observer {
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