package data

import model.MealItem
import java.io.File

class CsvDataRepository(private val filePath: String) {
    private val parser = MealCsvParser()

    fun getAllMeals(): List<MealItem> {
        return File(filePath).useLines { lines ->
            lines.drop(1)
                .map { line -> parser.parseOneLine(line) }
                .toList()
        }
    }
}