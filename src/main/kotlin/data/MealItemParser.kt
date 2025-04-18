package data

import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
class MealsCsvParser {

    fun parseOneLine(line: String): MealItem {

        val cleanedLine = line.trim().removeSuffix("\n")
        val fields = smartCsvParser(cleanedLine)
        if (fields.size < 12) {
            throw IllegalArgumentException("CSV line has fewer fields than expected: ${fields.size}")
        }

        return MealItem(
            name = fields[ColumnIndex.NAME],
            id = fields[ColumnIndex.ID].toInt(),
            minutes = fields[ColumnIndex.MINUTES].toInt(),
            contributorId = fields[ColumnIndex.CONTRIBUTOR_ID].toInt(),
            submitted = LocalDate.parse(fields[ColumnIndex.SUBMITTED]),
            tags = constructListFromString(fields[ColumnIndex.TAGS]),
            nutrition = constructNutritionObject(fields[ColumnIndex.NUTRITION]),
            stepNumbers = fields[ColumnIndex.NUMBER_OF_STEPS].toInt(),
            steps = constructListFromString(fields[ColumnIndex.STEPS]),
            description = fields[ColumnIndex.DESCRIPTION],
            ingredients = constructListFromString(fields[ColumnIndex.INGREDIENTS]),
            ingredientNumbers = fields[ColumnIndex.NUMBER_OF_INGREDIENTS].toInt()
        )
    }

    private fun constructNutritionObject(nutritionField: String): Nutrition {
        val nutrition = constructListFromString(nutritionField).map { it.toDouble() }

        return Nutrition(
            calories = nutrition[NutritionEntityIndex.CALORIES],
            totalFat = nutrition[NutritionEntityIndex.TOTAL_FAT],
            sugar = nutrition[NutritionEntityIndex.SUGAR],
            sodium = nutrition[NutritionEntityIndex.SODIUM],
            protein = nutrition[NutritionEntityIndex.PROTEIN],
            saturatedFat = nutrition[NutritionEntityIndex.SATURATED_FAT],
            carbohydrates = nutrition[NutritionEntityIndex.CARBOHYDRATES]
        )
    }

    private fun constructListFromString(string: String): List<String> {
        return string
            .removePrefix("[")
            .removeSuffix("]")
            .split(",")
            .map { it.trim().removeSurrounding(",") }
            .toList()
    }

    private fun smartCsvParser(line: String): List<String> {
        val mealEntities = mutableListOf<String>()
        val currentEntity = StringBuilder()
        var insideQuotes = false
        var i = 0

        while (i < line.length) {
            when (val char = line[i]) {
                '"' -> {
                    if (line[i + 1] == '"') {
                        currentEntity.append('"')
                        i++
                    } else {
                        insideQuotes = !insideQuotes
                    }
                }
                ',' -> {
                    if (!insideQuotes) {
                        mealEntities.add(currentEntity.toString().trim())
                        currentEntity.clear()
                    } else {
                        currentEntity.append(char)
                    }
                }
                else -> currentEntity.append(char)
            }
            i++
        }

        if (currentEntity.isNotEmpty()) {
            mealEntities.add(currentEntity.toString().trim())
        }

        return mealEntities
    }
}



