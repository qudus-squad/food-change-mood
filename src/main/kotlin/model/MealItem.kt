package model

import kotlinx.datetime.LocalDate

data class MealItem(
    val id: Int,
    val name: String,
    val minutes: Int,
    val contributorId: Int,
    val submitted: LocalDate,
    val tags: List<String>,
    val nutrition: Nutrition,
    val stepNumbers: Int,
    val steps: List<String>,
    val description: String,
    val ingredients: List<String>,
    val ingredientNumbers: Int
)