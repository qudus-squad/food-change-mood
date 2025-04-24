package logic

import kotlinx.datetime.LocalDate
import utils.Messages.INVALID_DATE_FORMAT

class DateFormatConverter {
    fun convertDate(dateString: String): LocalDate {
        try {
            return LocalDate.parse(dateString)
        } catch (e: Exception) {
            throw Exception(INVALID_DATE_FORMAT)
        }
    }
}