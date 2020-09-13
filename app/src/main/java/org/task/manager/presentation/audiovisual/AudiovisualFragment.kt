package org.task.manager.presentation.audiovisual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentAudiovisualBinding
import org.task.manager.domain.model.Audiovisual
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.presentation.view.SimpleDividerItemDecoration
import org.task.manager.presentation.view.SwipeCallback

class AudiovisualFragment : Fragment() {
    private val audiovisualViewModel: AudiovisualViewModel by viewModel()
    private lateinit var sharedViewModel: SharedViewModel
    private val dateService: DateService by inject()
    private lateinit var adapter: AudiovisualAdapter
    private lateinit var binding: FragmentAudiovisualBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audiovisual, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        var audiovisuals = mutableListOf<Audiovisual>()

        binding.addAudiovisual.setOnClickListener {
            navController.navigate(R.id.fragment_create_audiovisual)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_main)
        }

        audiovisualViewModel.audiovisuals.observe(viewLifecycleOwner, Observer {
            audiovisuals = it as MutableList<Audiovisual>
            adapter = AudiovisualAdapter(audiovisuals,audiovisualViewModel, sharedViewModel, dateService)
            binding.audiovisualList.adapter = adapter
            binding.audiovisualList.addItemDecoration(SimpleDividerItemDecoration(binding.root.context))
        })

        val swipeCallback = object : SwipeCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val id = audiovisuals[position].id
                audiovisualViewModel.deleteAudiovisual(id)
                Toast.makeText(binding.root.context,"Item removed",Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.fragment_audiovisual)
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.audiovisualList)
    }

}