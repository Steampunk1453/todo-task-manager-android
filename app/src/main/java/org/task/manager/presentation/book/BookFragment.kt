package org.task.manager.presentation.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_login.progressBar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentBookBinding
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.state.DeleteState
import org.task.manager.hide
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.presentation.view.SimpleDividerItemDecoration
import org.task.manager.presentation.view.SwipeCallback
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

private const val BOOK_REMOVE_MESSAGE = "Book removed"

class BookFragment : Fragment(), ViewElements {
    private lateinit var binding: FragmentBookBinding
    private lateinit var navController: NavController
    private lateinit var adapter: BookAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private val bookViewModel: BookViewModel by viewModel()
    private val dateService: DateService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false)
        navController = findNavController()
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel.getBooks()

        showProgress()
        observeBookViewModel()
        hideProgress()

        binding.addBook.setOnClickListener {
            navController.navigate(R.id.fragment_create_book)
        }

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

    private fun observeBookViewModel() {
        bookViewModel.books.observe(viewLifecycleOwner, {
            adapter = createAdapter(it, bookViewModel, sharedViewModel, dateService)
            buildBooksList()
            addRemoveItemEvent(it)
        })
    }

    private fun createAdapter(
        it: List<Book>,
        bookViewModel: BookViewModel,
        sharedViewModel: SharedViewModel,
        dateService: DateService
    ) =
        BookAdapter(it, bookViewModel, sharedViewModel, dateService)

    private fun buildBooksList() {
        binding.bookList.adapter = adapter
        binding.bookList.addItemDecoration(SimpleDividerItemDecoration(binding.root.context))
    }

    private fun addRemoveItemEvent(books: List<Book>) {
        val swipeCallback = object : SwipeCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val id = books[position].id
                bookViewModel.deleteBook(id)
                handleDeleteBook(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.bookList)
    }

    private fun handleDeleteBook(position: Int) {
        bookViewModel.deleteState.observe(viewLifecycleOwner, { state ->
            if (state == DeleteState.DELETE_COMPLETED) {
                showMessage(BOOK_REMOVE_MESSAGE)
                adapter.notifyItemRemoved(position)
                navController.navigate(R.id.fragment_book)
            }
        })
    }


}