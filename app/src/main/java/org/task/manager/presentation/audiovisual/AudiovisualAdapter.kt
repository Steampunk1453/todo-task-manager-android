package org.task.manager.presentation.audiovisual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_audiovisual.view.checkBox
//import kotlinx.android.synthetic.main.item_audiovisual.view.deadline
import kotlinx.android.synthetic.main.item_audiovisual.view.genre
import kotlinx.android.synthetic.main.item_audiovisual.view.platform
//import kotlinx.android.synthetic.main.item_audiovisual.view.startDate
import kotlinx.android.synthetic.main.item_audiovisual.view.title
import org.task.manager.R
import org.task.manager.domain.model.Audiovisual

class AudiovisualAdapter(private val audiovisuals: List<Audiovisual>) :
    RecyclerView.Adapter<AudiovisualAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            // Define click listener for the ViewHolder's View.
            view.checkBox.setOnCheckedChangeListener { checkbox, _ ->
                checkbox.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_audiovisual, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.title.text = audiovisuals[position].title
        holder.itemView.genre.text = audiovisuals[position].genre
        holder.itemView.platform.text = audiovisuals[position].platform
//        holder.itemView.startDate.text = audiovisuals[position].startDate
//        holder.itemView.deadline.text = audiovisuals[position].deadline
        holder.itemView.checkBox.isChecked = audiovisuals[position].check == 1
    }

    override fun getItemCount() = audiovisuals.size

}

