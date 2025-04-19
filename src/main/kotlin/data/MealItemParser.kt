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
            name = fields[ColumnIndex.NAME.index],
            id = fields[ColumnIndex.ID.index].toInt(),
            minutes = fields[ColumnIndex.MINUTES.index].toInt(),
            contributorId = fields[ColumnIndex.CONTRIBUTOR_ID.index].toInt(),
            submitted = LocalDate.parse(fields[ColumnIndex.SUBMITTED.index]),
            tags = constructListFromString(fields[ColumnIndex.TAGS.index]),
            nutrition = constructNutritionObject(fields[ColumnIndex.NUTRITION.index]),
            stepNumbers = fields[ColumnIndex.NUMBER_OF_STEPS.index].toInt(),
            steps = constructListFromString(fields[ColumnIndex.STEPS.index]),
            description = fields[ColumnIndex.DESCRIPTION.index],
            ingredients = constructListFromString(fields[ColumnIndex.INGREDIENTS.index]),
            ingredientNumbers = fields[ColumnIndex.NUMBER_OF_INGREDIENTS.index].toInt()
        )
    }

    private fun constructNutritionObject(nutritionField: String): Nutrition {
        val nutrition = constructListFromString(nutritionField).map { it.toDouble() }

        return Nutrition(
            calories = nutrition[NutritionEntityIndex.CALORIES.index],
            totalFat = nutrition[NutritionEntityIndex.TOTAL_FAT.index],
            sugar = nutrition[NutritionEntityIndex.SUGAR.index],
            sodium = nutrition[NutritionEntityIndex.SODIUM.index],
            protein = nutrition[NutritionEntityIndex.PROTEIN.index],
            saturatedFat = nutrition[NutritionEntityIndex.SATURATED_FAT.index],
            carbohydrates = nutrition[NutritionEntityIndex.CARBOHYDRATES.index]
        )
    }

    private fun constructListFromString(string: String): List<String> {
        return string
            .replace("[", "")
            .replace("]", "")
            .split(",")
            .map {
                it.trim().removeSurrounding("'")
            }
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



