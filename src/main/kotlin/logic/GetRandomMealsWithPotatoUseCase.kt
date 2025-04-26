package logic

import model.InvalidMealNumberException
import model.MealItem

class GetRandomMealsWithPotatoUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getPotatoMeals(
        randomMealsCount: Int = MAXIMUM_MEALS_TO_SELECT,
        mealName: String = POTATO
    ): List<MealItem> {
        if (randomMealsCount < 0) {
            throw InvalidMealNumberException(MEALS_NUMBER_CANNOT_BE_NEGATIVE)
        }
        val potatoMeals = dataSource.getAllMeals()
            .filter { meal ->
                hasPotato(meal, mealName)
            }

        return potatoMeals
            .take(randomMealsCount)
    }

    private fun hasPotato(meal: MealItem, mealName: String): Boolean {
        return meal.ingredients.any { it.contains(mealName, ignoreCase = true) }
    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
        const val POTATO = "potato"
        const val MEALS_NUMBER_CANNOT_BE_NEGATIVE = "Meal number cannot be negative"
    }

}