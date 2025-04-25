package model

import kotlinx.datetime.LocalDate

data class MealItem(
    val id: Int,
    val name: String,
    val preparationTimeInMinutes: Int,
    val contributorId: Int,
    val submitted: LocalDate,
    val mealTags: List<String>,
    val nutrition: Nutrition,
    val steps: List<String>,
    val stepNumbers: Int,
    val description: String,
    val ingredients: List<String>,
    val ingredientNumbers: Int
)