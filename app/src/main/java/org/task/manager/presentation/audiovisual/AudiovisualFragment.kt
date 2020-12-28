package org.task.manager.presentation.audiovisual

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
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentAudiovisualBinding
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.state.DeleteState
import org.task.manager.hide
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.presentation.view.SimpleDividerItemDecoration
import org.task.manager.presentation.view.SwipeCallback
import org.task.manager.presentation.view.ViewElements
import org.task.manager.show

private const val ITEM_REMOVE_MESSAGE = "Item removed"

class AudiovisualFragment : Fragment(), ViewElements {

    private lateinit var binding: FragmentAudiovisualBinding
    private lateinit var navController: NavController
    private lateinit var adapter: AudiovisualAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private val audiovisualViewModel: AudiovisualViewModel by viewModel()
    private val dateService: DateService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audiovisual, container, false)
        navController = findNavController()
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        audiovisualViewModel.getAudiovisuals()

        showProgress()
        observeAudiovisualViewModel()
        hideProgress()

        binding.addAudiovisual.setOnClickListener {
            navController.navigate(R.id.fragment_create_audiovisual)
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

    private fun observeAudiovisualViewModel() {
        audiovisualViewModel.audiovisuals.observe(viewLifecycleOwner, {
            adapter = createAdapter(it, sharedViewModel, dateService)
            buildAudiovisualsList(adapter)
            addRemoveItemEvent(it)
        })
    }

    private fun createAdapter(
        audiovisuals: List<Audiovisual>,
        sharedViewModel: SharedViewModel,
        dateService: DateService
    ) =
        AudiovisualAdapter(audiovisuals, sharedViewModel, dateService)

    private fun buildAudiovisualsList(adapter: AudiovisualAdapter) {
        binding.audiovisualList.adapter = adapter
        binding.audiovisualList.addItemDecoration(SimpleDividerItemDecoration(binding.root.context))
    }

    private fun addRemoveItemEvent(audiovisuals: List<Audiovisual>) {
        val swipeCallback = object : SwipeCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val id = audiovisuals[position].id
                id?.let { audiovisualViewModel.deleteAudiovisual(it) }
                handleDeleteAudiovisual(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.audiovisualList)
    }

    private fun handleDeleteAudiovisual(position: Int) {
        audiovisualViewModel.deleteState.observe(viewLifecycleOwner, { state ->
            if (state == DeleteState.DELETE_COMPLETED) {
                showMessage(ITEM_REMOVE_MESSAGE)
                this@AudiovisualFragment.adapter.notifyItemRemoved(position)
                navController.navigate(R.id.fragment_audiovisual)
            }
        })
    }

}