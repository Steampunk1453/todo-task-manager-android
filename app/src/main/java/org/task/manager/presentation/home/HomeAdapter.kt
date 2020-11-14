package org.task.manager.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_calendar.view.itemFlightDateText
import org.task.manager.R
import org.task.manager.presentation.shared.CalendarItem
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.ItemType
import org.task.manager.shared.Constants

class HomeAdapter(private val calendarItems: List<CalendarItem>,
                  private val viewModel: HomeViewModel,
                  private val dateService : DateService
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_calendar, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        populateItemView(holder, position)

        holder.itemView.setOnClickListener {
            val itemType = calendarItems[position].itemType
            val itemId = calendarItems[position].id

            if (itemType == ItemType.AUDIOVISUAL) {
                handleAudiovisualItem(itemId, holder)
            } else {
                handleBookItem(itemId, holder)
            }
            return@setOnClickListener
        }
    }

    override fun getItemCount() = calendarItems.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun populateItemView(holder: ViewHolder, position: Int) {
        holder.itemView.itemFlightDateText.text = calendarItems[position].title
        calendarItems[position].check == Constants.TRUE
    }

    private fun handleAudiovisualItem(id: Long, holder: ViewHolder) {
        viewModel.getAudiovisual(id)

        viewModel.audiovisual.observe(holder.itemView.context as LifecycleOwner, {
            viewModel.sendAudiovisual(it)
            val bundle = bundleOf("action" to "update")
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.fragment_create_audiovisual, bundle)
        })
    }

    private fun handleBookItem(id: Long, holder: ViewHolder) {
        viewModel.getBook(id)

        viewModel.book.observe(holder.itemView.context as LifecycleOwner, {
            viewModel.sendBook(it)
            val bundle = bundleOf("action" to "update")
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.fragment_create_book, bundle)
        })
    }

}