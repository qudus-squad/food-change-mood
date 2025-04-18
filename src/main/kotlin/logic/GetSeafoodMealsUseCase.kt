package logic

import model.MealItem
import model.SeafoodMealItem
import utils.ListUtils.orThrowIfEmpty

class GetSeafoodMealsUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    fun getSeafoodMeals(
        seafoodKeywords: List<String> = listOf(
            "fish", "shrimp", "crab", "lobster", "salmon", "tuna", "clam", "oyster", "scallop", "squid"
        )
    ): List<SeafoodMealItem> {
        return meals
            .filter { isSeafoodMeal(it , seafoodKeywords) }
            .orThrowIfEmpty { NoSeafoodMealsFoundException("No seafood meals found") }
            .sortedByDescending { it.nutrition.protein }
            .mapIndexed { index, meal ->
                SeafoodMealItem(
                    rank = index + 1,
                    name = meal.name,
                    protein = meal.nutrition.protein
                )
            }
    }

    private fun isSeafoodMeal(meal: MealItem ,seafoodKeywords : List<String> ): Boolean {

        return meal.ingredients.any { ingredient ->
            seafoodKeywords.any { keyword ->
                ingredient.lowercase().contains(keyword)
            }
        }
    }
}

class NoSeafoodMealsFoundException(message: String) : Exception(message)