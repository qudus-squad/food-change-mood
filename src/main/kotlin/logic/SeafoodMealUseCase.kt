package logic

import model.MealItem
import model.SeafoodMealItem
import utils.ListUtils.orThrowIfEmpty

class SeafoodMealUseCase(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun getSeafoodMeals(): List<SeafoodMealItem> {
        return meals
            .filter { isSeafoodMeal(it) }
            .orThrowIfEmpty { NoSeafoodMealsFoundException("No seafood meals found") }
            .sortedByDescending { it.nutrition.protein }
            // Converts meals to SeafoodMealItem with ranking based on protein order.
            .mapIndexed { index, meal ->
                SeafoodMealItem(
                    rank = index + 1,
                    name = meal.name,
                    protein = meal.nutrition.protein
                )
            }

    }


    // Checks if the meal contains any seafood-related ingredient based on known keywords.
    private fun isSeafoodMeal(meal: MealItem): Boolean {
        val seafoodKeywords = listOf(
            "fish", "shrimp", "crab", "lobster", "salmon", "tuna", "clam", "oyster", "scallop", "squid"
        )
        return meal.ingredients.any { ingredient ->
            seafoodKeywords.any { keyword ->
                ingredient.lowercase().contains(keyword)
            }
        }
    }

}

class NoSeafoodMealsFoundException(message: String) : Exception(message)