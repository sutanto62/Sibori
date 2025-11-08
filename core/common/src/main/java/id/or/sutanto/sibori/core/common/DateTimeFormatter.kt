package id.or.sutanto.sibori.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeFormatterUtils {
    fun formatDayAndTime(epochMillis: Long, locale: Locale = Locale.getDefault()): String {
        val day = SimpleDateFormat("EEE, dd MMM", locale).format(Date(epochMillis))
        val time = SimpleDateFormat("HH:mm", locale).format(Date(epochMillis))
        return "$day, $time"
    }
}
