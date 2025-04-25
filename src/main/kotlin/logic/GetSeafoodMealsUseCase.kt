package logic

import model.MealItem
import utils.ListUtils.orThrowIfEmpty
import utils.Strings.SEAFOOD_KEYWORDS

class GetSeafoodMealsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getSeafoodMeals(
        randomMealsCount: Int = MAXIMUM_MEALS_TO_SELECT,
        seafoodKeywords: List<String> = SEAFOOD_KEYWORDS
    ): List<MealItem> {
        return dataSource.getAllMeals()
            .filter { meal-> isSeafoodMeal(meal, seafoodKeywords) }
            .sortedByDescending { meal-> meal.nutrition.protein }
            .take(randomMealsCount)
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
