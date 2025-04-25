package utils

import model.MealItem

object Utils {

    fun printMealDetails(meal: MealItem) {
        println("---- Meal Details ----")
        println("Name: ${meal.name}")
        println("Submitted on: ${meal.submitted}")
        println("Preparation Time: ${meal.preparationTimeInMinutes} minutes")
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
        meal.mealTags.forEach {
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
    const val NO_MORE_HIGH_CALORIE_MEALS = "No more high-calorie meals available to suggest."
}

object Strings {
    const val SWEETS_KEYWORDS = "sweet"
    const val EGGS_KEYWORDS = "eggs"
    val SEAFOOD_KEYWORDS: List<String> =
        listOf("fish", "shrimp", "crab", "lobster", "salmon", "tuna", "clam", "oyster", "scallop", "squid")
}