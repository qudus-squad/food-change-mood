package logic

import kotlinx.datetime.LocalDate
import utils.Messages.INVALID_DATE_FORMAT
// unnecessary for the Logic layer
// why using DataFormatConverter in logic instead of utils → problems in dependency inversion
class DateFormatConverter {
    fun convertDate(dateString: String): LocalDate {
        try {
            return LocalDate.parse(dateString)
        } catch (e: Exception) {
            throw Exception(INVALID_DATE_FORMAT)
        }
    }
}

