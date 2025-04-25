package utils

import model.MealItem

object Utils {

    fun printMealDetails(meal: MealItem) {
        println("---- Meal Details ----")
        println("Name: ${meal.name}")
        println("Submitted on: ${meal.submitted}")
        println("Preparation Time: ${meal.minutes} minutes")
        println("\nDescription:")
        println(meal.description)
        println("\nIngredients (${meal.ingredientNumbers}):")
        meal.ingredients.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        }
        println("\nSteps (${meal.stepNumbers}):")
        meal.steps.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        }
        println("\nTags:")
        meal.tags.forEach {
            println("- $it")
        }
        println("\nNutrition Information:")
        println("Calories: ${meal.nutrition.calories}")
        println("Total Fat: ${meal.nutrition.totalFat}g")
        println("Saturated Fat: ${meal.nutrition.saturatedFat}g")
        println("Carbohydrates: ${meal.nutrition.carbohydrates}g")
        println("Sugar: ${meal.nutrition.sugar}g")
        println("Sodium: ${meal.nutrition.sodium}mg")
        println("Protein: ${meal.nutrition.protein}g")
    }

    fun printMealNameAndDescription(meal: MealItem) {
        println("---- Meal Details ----")
        println("Name: ${meal.name}")
        println("Description:")
        println(meal.description + "\n")
    }

}

object Messages {
    const val NO_MEALS_FOND_FOR_COUNTRY = "No meals found for country: "
    const val NO_SUGGESTION_MEALS = "No meals found for suggestion meals"
    const val NUMBER_OF_SUGGESTIONS = "Invalid number of suggestions : must be greater than 0"
    const val INVALID_PREPARATION_TIME = "Invalid meal preparation time: must be greater than 0"
    const val NUMBER_OF_INGREDIENTS = "Invalid number of ingredients: cannot be negative"
    const val NUMBER_OF_PREPARATION_STEPS = "Invalid number of preparation steps: cannot be negative"
    const val NO_MEALS_FOR_LARGE_GROUP_FOND_FOR_COUNTRY = "No meals found for country: "
    const val NO_EGG_FREE_SWEETS = "No egg-free sweets found "

    const val INVALID_MEAL_NAME = "name is empty or contains invalid characters"
    const val NO_MEALS_FOUND_FOR_NAME = "No meals found for this name"
    const val NO_MEALS_WITH_POTATO_FOUND = "No meals with potato found"
    const val MEALS_NUMBER_CANNOT_BE_NEGATIVE = "Meal number cannot be negative"
    const val NO_MORE_HIGH_CALORIE_MEALS = "No more high-calorie meals available to suggest."
    const val NO_SEAFOOD_MEALS_FOUND = "No more high-calorie meals available to suggest."
    const val NO_MEALS_FOR_KETO_DIET = "There are no meals suitable for the keto diet."
    const val NO_MEALS_FOR_ADD_DATE = "No meals found for this date"
    const val INVALID_DATE_FORMAT = "invalid date format"
}

object Strings {
    const val FOR_LARGE_GROUP = "for-large-groups"
    const val ITALIAN = "italian"
    const val SWEETS_KEYWORDS = "sweet"
    const val EGGS_KEYWORDS = "eggs"
    val SEAFOOD_KEYWORDS: List<String> =
        listOf("fish", "shrimp", "crab", "lobster", "salmon", "tuna", "clam", "oyster", "scallop", "squid")
    const val POTATO = "potato"
}