package org.task.manager.presentation.audiovisual

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_audiovisual.view.checkBox
import kotlinx.android.synthetic.main.item_audiovisual.view.deadline
import kotlinx.android.synthetic.main.item_audiovisual.view.genre
import kotlinx.android.synthetic.main.item_audiovisual.view.platform
import kotlinx.android.synthetic.main.item_audiovisual.view.startDate
import kotlinx.android.synthetic.main.item_audiovisual.view.title
import org.task.manager.R
import org.task.manager.domain.model.Audiovisual
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.shared.Constants.TRUE

class AudiovisualAdapter(private val audiovisuals: List<Audiovisual>,
                         private val audiovisualViewModel: AudiovisualViewModel,
                         private val sharedViewModel: SharedViewModel,
                         private val dateService : DateService
) : RecyclerView.Adapter<AudiovisualAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_audiovisual, parent, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        populateItemView(holder, position)

        holder.itemView.setOnLongClickListener {
            audiovisualViewModel.getAudiovisual(audiovisuals[position].id)

            audiovisualViewModel.audiovisual.observe(holder.itemView.context as LifecycleOwner, Observer {
                sharedViewModel.sendAudiovisual(it)

                val bundle = bundleOf("action" to "update")
                Navigation.findNavController(holder.itemView).navigate(R.id.fragment_create_audiovisual, bundle)
            })
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = audiovisuals.size

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateItemView(holder: ViewHolder, position: Int) {
        holder.itemView.title.text = audiovisuals[position].title
        holder.itemView.genre.text = audiovisuals[position].genre
        holder.itemView.platform.text = audiovisuals[position].platform
        holder.itemView.startDate.text = dateService.getFormattedDate(audiovisuals[position].startDate)
        holder.itemView.deadline.text = dateService.getFormattedDate(audiovisuals[position].deadline)
        holder.itemView.checkBox.isChecked = audiovisuals[position].check == TRUE
    }

}

