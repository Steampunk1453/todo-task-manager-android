package org.task.manager.presentation.home

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import org.task.manager.R
import org.task.manager.databinding.ItemCalendarBinding
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Book
import org.task.manager.presentation.calendar.getColorCompat
import org.task.manager.presentation.calendar.getDrawableCompat
import org.task.manager.presentation.calendar.layoutInflater
import org.task.manager.presentation.shared.CalendarItem
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.ItemType
import org.task.manager.presentation.shared.SharedViewModel

class HomeAdapter(
    val calendarItems: MutableList<CalendarItem>,
    private val viewModel: SharedViewModel,
    private val dateService : DateService
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalendarBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(calendarItems[position])

        holder.itemView.setOnClickListener {
            val itemType = calendarItems[position].itemType
            val calendarItem = calendarItems[position]

            if (itemType == ItemType.AUDIOVISUAL) {
                calendarItem.audiovisual?.let { aud -> handleAudiovisualItem(aud, holder.itemView) }
            } else if (itemType == ItemType.BOOK) {
                calendarItem.book?.let { book -> handleBookItem(book, holder.itemView) }
            }
            return@setOnClickListener
        }
    }

    override fun getItemCount() = calendarItems.size

    @RequiresApi(Build.VERSION_CODES.O)
    inner class ViewHolder(val binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarItem: CalendarItem) {
            binding.startDateItem.apply {
                text = dateService.getFormattedDateWithWeekDay(calendarItem.startDate)
                setBackgroundColor(itemView.context.getColorCompat(calendarItem.color))
            }
            binding.deadlineDateItem.apply {
                text = dateService.getFormattedDateWithWeekDay(calendarItem.endDate)
                setBackgroundColor(itemView.context.getColorCompat(calendarItem.color))
            }
            binding.image.setImageDrawable(itemView.context.getDrawableCompat(calendarItem.icon))
            binding.titleItem.text = calendarItem.title
        }
    }

    private fun handleAudiovisualItem(audiovisual: Audiovisual, itemView: View) {
        viewModel.sendAudiovisual(audiovisual)
        val bundle = bundleOf("action" to "update")
        Navigation.findNavController(itemView).navigate(R.id.fragment_create_audiovisual, bundle)
    }

    private fun handleBookItem(book: Book, itemView: View) {
        viewModel.sendBook(book)
        val bundle = bundleOf("action" to "update")
        Navigation.findNavController(itemView).navigate(R.id.fragment_create_book, bundle)
    }

}