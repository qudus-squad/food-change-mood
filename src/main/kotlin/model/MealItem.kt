package model

import kotlinx.datetime.LocalDate

data class MealItem(
    val id: Int,
    val name: String,
    val minutes: Int, // wrong naming
    val contributorId: Int,
    val submitted: LocalDate, // Long || TimeStamp
    val tags: List<String>, // clarify "tags of what?"
    val nutrition: Nutrition,
    val stepNumbers: Int, // spelling missed up stepsNumber
    val steps: List<String>,
    val description: String,
    val ingredients: List<String>,
    val ingredientNumbers: Int // spelling missed up ingredientsNumber
)