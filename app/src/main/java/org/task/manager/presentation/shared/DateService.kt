package org.task.manager.presentation.shared

import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

private const val INSTANT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val SPANISH_DATE_PATTERN = "dd/MM/yy"
private const val WEEK_DAY_PATTERN = "EEE'\n'dd MMM'\n'HH:mm"
private const val MONTH_PATTERN = "MMMM"

class DateService {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(date: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern(INSTANT_PATTERN, Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern(SPANISH_DATE_PATTERN, Locale.ENGLISH)
        val localDate = LocalDate.parse(date, inputFormatter)
        return outputFormatter.format(localDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToMilliseconds(date: String): Long {
        val formatter = DateTimeFormatter.ofPattern(SPANISH_DATE_PATTERN, Locale.ENGLISH)
        val localDate = LocalDate.parse(date, formatter)
        val instant = localDate.atTime(LocalTime.NOON).atZone(ZoneId.systemDefault()).toInstant()
        return instant.toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(INSTANT_PATTERN, Locale.ENGLISH)
        return LocalDate.parse(date, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToInstant(dateMilliseconds: Long): String =
        Timestamp(dateMilliseconds).toInstant().toString()

    fun addHours(hours: Long) = TimeUnit.HOURS.toMillis(hours)

    fun getFormattedDateWithWeekDay(date: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern(INSTANT_PATTERN, Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern(WEEK_DAY_PATTERN, Locale.ENGLISH)
        val localDateTime = LocalDateTime.parse(date, inputFormatter)
        return outputFormatter.format(localDateTime)
    }

    fun getFormattedMonth(yearMonth: YearMonth): String {
       val formatter = DateTimeFormatter.ofPattern(MONTH_PATTERN)
        return formatter.format(yearMonth)
    }

}