package model

/**
 * The Meal data class represents a meal item with various attributes.
 *
 * @property id the unique identifier for the meal.
 * @property name the name of the meal.
 * @property description a short description of the meal. May be null.
 * @property nutrition an optional Nutrition object holding the meal's nutritional data.
 * @property prepTime the preparation time in minutes.
 * @property ingredients a list of ingredients required for the meal.
 * @property steps a list of steps for preparing the meal.
 * @property addDate the date the meal was added (using LocalDate).
 * @property categories a list of categories or tags associated with the meal.
 */
data class Meal(
    val id: Int,
    val name: String,
    val description: String?,
    val nutrition: Nutrition?,
    val prepTime: Int,
    val ingredients: List<String>,
    val steps: List<String>,
    val addDate: LocalDate,
    val categories: List<String>
    val contributorId: Int? = null,
    val submitted: String? = null,
    val tags: List<String>? = null,
    val stepNumbers: Int? = null,
    val ingredientNumbers: Int? = null
)