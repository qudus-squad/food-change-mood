package model

/**
 * The Nutrition data class represents the nutritional information for a meal.
 *
 * @property calories the number of calories in the meal.
 * @property totalFat the total fat content in grams.
 * @property sugar the sugar content in grams.
 * @property sodium the sodium content in milligrams.
 * @property protein the protein content in grams.
 * @property saturatedFat the saturated fat content in grams.
 * @property carbohydrates the carbohydrate content in grams.
 */
data class Nutrition(
    val calories: Int,
    val totalFat: Double,
    val sugar: Double,
    val sodium: Double,
    val protein: Double,
    val saturatedFat: Double,
    val carbohydrates: Double
)