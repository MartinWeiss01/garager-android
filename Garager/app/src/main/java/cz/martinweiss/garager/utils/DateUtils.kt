package cz.martinweiss.garager.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {
    companion object {
        private val DATE_FORMAT_CS = "dd. MM. yyyy"
        private val TIMEDATE_FORMAT_CS = "dd. MM. yyyy HH:mm"
        private val DATE_FORMAT_EN = "yyyy/MM/dd"
        private val TIMEDATE_FORMAT_EN = "yyyy/MM/dd HH:mm"

        fun getDateString(unixTime: Long, includeTime: Boolean = false): String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = unixTime
            val format: SimpleDateFormat
            if (LanguageUtils.isLanguageCzech()){
                format = if(includeTime) SimpleDateFormat(TIMEDATE_FORMAT_CS, Locale.GERMAN)
                else SimpleDateFormat(DATE_FORMAT_CS, Locale.GERMAN)
            } else {
                format = if(includeTime) SimpleDateFormat(TIMEDATE_FORMAT_EN, Locale.ENGLISH)
                else SimpleDateFormat(DATE_FORMAT_EN, Locale.ENGLISH)
            }
            return format.format(calendar.getTime())
        }

        fun getUnixTime(year: Int, month: Int, day: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return calendar.timeInMillis
        }

        fun getRemainingDays(timestamp: Long): Int {
            val currentTimestamp = System.currentTimeMillis()
            val diff = timestamp - currentTimestamp
            return TimeUnit.MILLISECONDS.toDays(diff).toInt()
        }
    }
}