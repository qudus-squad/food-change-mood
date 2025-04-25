package logic

import kotlinx.datetime.LocalDate

class DateFormatConverter {
    fun convertDate(dateString: String): LocalDate {
        try {
            return LocalDate.parse(dateString)
        } catch (e: Exception) {
            throw Exception(INVALID_DATE_FORMAT)
        }
    }
    companion object {
        const val INVALID_DATE_FORMAT = "invalid date format"
    }
}