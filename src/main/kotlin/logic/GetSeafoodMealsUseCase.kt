package logic

import model.MealItem
import model.NoSeafoodMealsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.NO_SEAFOOD_MEALS_FOUND
import utils.Strings.SEAFOOD_KEYWORDS

class GetSeafoodMealsUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    fun getSeafoodMeals(
        seafoodKeywords: List<String> = SEAFOOD_KEYWORDS
    ): List<MealItem> {
        return meals
            .filter { isSeafoodMeal(it, seafoodKeywords) }
            .sortedByDescending { it.nutrition.protein }
            .shuffled()
            .orThrowIfEmpty { NoSeafoodMealsFoundException(NO_SEAFOOD_MEALS_FOUND) }
    }

    private fun isSeafoodMeal(meal: MealItem, seafoodKeywords: List<String>): Boolean {
        return meal.ingredients.any { ingredient ->
            seafoodKeywords.any { keyword ->
                ingredient.lowercase().contains(keyword)
            }
        }
    }
}