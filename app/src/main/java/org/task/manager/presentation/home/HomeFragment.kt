package org.task.manager.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.fragment_home.calendarView
import kotlinx.android.synthetic.main.fragment_home.user
import org.task.manager.R
import org.task.manager.databinding.FragmentHomeBinding
import org.task.manager.presentation.shared.SharedViewModel
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.navigate(R.id.fragment_main)
        }

        sharedViewModel.userData.observe(viewLifecycleOwner, {
            if(it != null) {
                val prefix = user.text
                val username = it
                val message = "$prefix $username"
                user.text = message
            }
        })

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(Color.CYAN)
                } else {
                    container.textView.setTextColor(Color.MAGENTA)
                }
            }
        }

//        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
//            // Called only when a new container is needed.
//            override fun create(view: View) = DayViewContainer(view)
//
//            // Called every time we need to reuse a container.
//            override fun bind(container: DayViewContainer, day: CalendarDay) {
//                container.textView.text = day.date.dayOfMonth.toString()
//            }
//        }

    }

}

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView: TextView = view.findViewById<TextView>(R.id.calendarDayText)

    // With ViewBinding
//     val textView = Example5CalendarDayBinding.bind(view).exFiveDayText

}
