package org.task.manager.presentation.audiovisual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentAudiovisualBinding

class AudiovisualFragment : Fragment() {
    private val viewModel: AudiovisualViewModel by viewModel()
    private lateinit var adapter: AudiovisualAdapter
    private lateinit var binding: FragmentAudiovisualBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audiovisual, container, false)
        navController = findNavController()

        viewModel.getAudiovisuals()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_main)
        }

        viewModel.audiovisuals.observe(viewLifecycleOwner, Observer {
            adapter = AudiovisualAdapter(it)
            binding.audiovisualList.adapter = adapter
        })

    }

}