package data

import model.MealItem
import model.Nutrition
import kotlin.io.path.Path
import kotlin.io.path.readText

class MealCsvParser {
    fun filterData() {

        val path = Path("food (1).csv")
        val content = path.readText()
        content.forEach {
            println(it)
        }
    }


    fun parseOneLine(line: String): MealItem {

        val meal = splitCsvLineData(line)

        return MealItem(
            name = meal[0],
            id = meal[1].toInt(),
            minutes = meal[2].toInt(),
            contributorId = meal[3].toInt(),
            submitted = meal[4],
            tags = meal[5].parseStringList(),
            nutrition = meal[6].parseDoubleList().constructNutritionData(),
            stepNumbers = meal[7].toInt(),
            steps = meal[8].parseStringList(),
            description = meal[9],
            ingredients = meal[10].parseStringList(),
            ingredientNumbers = meal[11].toInt(),
        )
    }

    fun splitCsvLineData(line: String): List<String> {
        val string = line.trim()
        val fields = mutableListOf<String>()
        var currentField = ""
        var quotes = false
        var brackets = false
        var bracketDepth = 0

        for (char in line) {
            when (char) {
                '"' -> {
                    quotes = !quotes
                    currentField += char
                }
                '[' -> {
                    bracketDepth++
                    brackets = bracketDepth > 0
                    currentField+= char
                }
                ']' -> {
                    bracketDepth--
                    brackets = bracketDepth > 0
                    currentField+= char
                }
                ',' -> {
                    if (!quotes && !brackets) {

                        fields.add(currentField.trim())
                        currentField =""
                    } else {
                        currentField+= char
                    }
                }
                else -> {
                    currentField+= char
                }
            }
        }

        if (currentField.isNotEmpty()) {
            fields.add(currentField.trim())
        }

        return fields
    }
    private fun String.parseStringList(): List<String> {
        val cleaned = this
            .trim()
            .removeSurrounding("\"")
            .removeSurrounding("[", "]")
        val items = cleaned.split(",")
        val result = mutableListOf<String>()

        for (item in items) {
            val trimmedItem = item.trim().removeSurrounding("'")
            result.add(trimmedItem)
        }
        return result
    }

    private fun String.parseDoubleList(): List<Double> {
        val cleaned = this
            .trim()
            .removeSurrounding("\"")
            .removeSurrounding("[", "]")
        val items = cleaned.split(",")
        val result = mutableListOf<Double>()

        for (item in items) {
            val trimmedItem = item.trim().removeSurrounding("'")
            result.add(trimmedItem.toDouble())
        }
        return result
    }

    private fun List<Double>.constructNutritionData(): Nutrition {
        val nutritionData = this
        return Nutrition(
            calories = nutritionData[0],
            totalFat = nutritionData[1],
            sugar = nutritionData[2],
            sodium = nutritionData[3],
            protein = nutritionData[4],
            saturatedFat = nutritionData[5],
            carbohydrates = nutritionData[6]
        )
    }
}