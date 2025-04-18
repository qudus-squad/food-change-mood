package data

import logic.FoodChangeModeDataSource
import model.MealItem

class CsvFileDataSource(
    private val csvFileReader: CsvFileReader,
    private val citiesCsvParsers: MealsCsvParser
):FoodChangeModeDataSource {
    override fun getAllMeals(): List<MealItem> {
        return csvFileReader.readLineFromFile().mapNotNull { line ->
            try {
                citiesCsvParsers.parseOneLine(line)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}