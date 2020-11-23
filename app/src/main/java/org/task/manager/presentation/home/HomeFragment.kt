package org.task.manager.presentation.home

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.fragment_home.calendarView
import kotlinx.android.synthetic.main.fragment_home.welcome
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.task.manager.R
import org.task.manager.databinding.FragmentHomeBinding
import org.task.manager.databinding.LayoutCalendarDayBinding
import org.task.manager.databinding.LayoutCalendarHeaderBinding
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Book
import org.task.manager.presentation.calendar.daysOfWeekFromLocale
import org.task.manager.presentation.calendar.getColorCompat
import org.task.manager.presentation.calendar.setTextColorRes
import org.task.manager.presentation.shared.CalendarItem
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.ItemType
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.presentation.view.SimpleDividerItemDecoration
import org.task.manager.shared.Constants.FALSE
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private const val BLUE_COLOR =  R.color.blue_800
private const val TEAL_COLOR =  R.color.teal_700
private const val VIDEO_ICON =  R.drawable.ic_video_library_24px
private const val BOOK_ICON =  R.drawable.ic_books_library_24dp

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var adapter: HomeAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private var calendarItems: List<CalendarItem> = listOf()
    private var calendarItemsMap: Map<LocalDate, List<CalendarItem>> = mapOf()
    private var selectedDate: LocalDate? = null
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val homeViewModel: HomeViewModel by viewModel()
    private val dateService: DateService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        navController = findNavController()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        homeViewModel.getAudiovisuals()
        homeViewModel.getBooks()

        observeHomeViewModel()

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        calendarView.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
        calendarView.scrollToMonth(currentMonth)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_main)
        }

        sharedViewModel.userData.observe(viewLifecycleOwner, {
            if(it != null) {
                val prefix = welcome.text
                val username = it
                val message = "$prefix $username"
                welcome.text = message
            }
        })

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = LayoutCalendarDayBinding.bind(view)
            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            val binding = this@HomeFragment.binding
                            binding.calendarView.notifyDateChanged(day.date)
                            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
                            binding.homeRecyclerView.adapter = adapter
                            binding.homeRecyclerView.addItemDecoration(SimpleDividerItemDecoration(binding.root.context))
                            updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)
            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                homeViewModel.audiovisuals.observe(viewLifecycleOwner, { audiovisuals ->
                    homeViewModel.books.observe(viewLifecycleOwner, { books ->
                        calendarItems = getCalendarItems(audiovisuals, books)
                        calendarItemsMap = calendarItems.groupBy { dateService.getLocalDate(it.startDate) }

                        container.day = day
                        val textView = container.binding.dayText
                        val layout = container.binding.dayLayout
                        textView.text = day.date.dayOfMonth.toString()

                        val topView = container.binding.dayTop
                        val bottomView = container.binding.dayBottom
                        topView.background = null
                        bottomView.background = null

                        if (day.owner == DayOwner.THIS_MONTH) {
                            textView.setTextColorRes(R.color.black)
                            layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.example_5_selected_bg else 0)

                            val items = calendarItemsMap[day.date]
                            if (items != null) {
                                if (items.count() == 1) {
                                    bottomView.setBackgroundColor(view.context.getColorCompat(items[0].color))
                                } else {
                                    topView.setBackgroundColor(view.context.getColorCompat(items[0].color))
                                    bottomView.setBackgroundColor(view.context.getColorCompat(items[1].color))
                                }
                            }
                        } else {
                            textView.setTextColorRes(R.color.text_grey_light)
                            layout.background = null
                        }

                    })
                })

            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val monthLayout = LayoutCalendarHeaderBinding.bind(view).calendarHeader.root
        }

        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.monthLayout.tag == null) {
                    container.monthLayout.tag = month.yearMonth
                    container.monthLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                            .toUpperCase(Locale.ENGLISH)
                        tv.setTextColorRes(R.color.black)
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    }
                    month.yearMonth
                }
            }
        }

        calendarView.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            binding.monthYear.text = title

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                calendarView.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }

        binding.nextMonthImage.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        binding.previousMonthImage.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeHomeViewModel() {
        homeViewModel.audiovisuals.observe(viewLifecycleOwner, { audiovisuals ->
            homeViewModel.books.observe(viewLifecycleOwner, { books ->
                val calendarItems = getCalendarItems(audiovisuals, books)
                calendarItemsMap = calendarItems.groupBy { dateService.getLocalDate(it.startDate) }

                adapter = createAdapter(calendarItems, homeViewModel, dateService)
            })
        })
    }

    private fun createAdapter(
        calendarItems: MutableList<CalendarItem>,
        viewModel: HomeViewModel,
        dateService: DateService
    ) = HomeAdapter(calendarItems, viewModel, dateService)

    private fun getCalendarItems(audiovisuals: List<Audiovisual>, books: List<Book>) : MutableList<CalendarItem> {
        val calendarItems = mutableListOf<CalendarItem>()

        val audiovisualsItems = audiovisuals.filter { it.check == FALSE }
            .map { CalendarItem(it.id, it.title, it.startDate, it.deadline, ItemType.AUDIOVISUAL,
               BLUE_COLOR, VIDEO_ICON) }

        val booksItems = books.filter { it.check == FALSE }
            .map { CalendarItem(it.id, it.title, it.startDate, it.deadline, ItemType.BOOK,
                TEAL_COLOR, BOOK_ICON) }

        calendarItems.addAll(audiovisualsItems + booksItems)

        return calendarItems
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        adapter.calendarItems.clear()
        adapter.calendarItems.addAll(calendarItemsMap[date].orEmpty())
        adapter.notifyDataSetChanged()
    }
}



