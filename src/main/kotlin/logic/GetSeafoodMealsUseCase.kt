package logic

import model.MealItem
import model.NoSeafoodMealsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.NO_SEAFOOD_MEALS_FOUND
import utils.Strings.SEAFOOD_KEYWORDS

class GetSeafoodMealsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getSeafoodMeals(
        randomMealsNumber: Int = MAXIMUM_MEALS_TO_SELECT, // rename to randomMealsCount
        seafoodKeywords: List<String> = SEAFOOD_KEYWORDS
    ): List<MealItem> {
        return dataSource.getAllMeals()
            .filter { isSeafoodMeal(it, seafoodKeywords) }
            .sortedByDescending { it.nutrition.protein }
            .shuffled() // don't use shuffle
            .take(randomMealsNumber)
            .orThrowIfEmpty { NoSeafoodMealsFoundException(NO_SEAFOOD_MEALS_FOUND) } // no exception needed for empty
    }

    private fun isSeafoodMeal(meal: MealItem, seafoodKeywords: List<String>): Boolean {
        return meal.ingredients.any { ingredient ->
            seafoodKeywords.any { keyword ->
                ingredient.lowercase().contains(keyword)
            }
        }
    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
    }

}