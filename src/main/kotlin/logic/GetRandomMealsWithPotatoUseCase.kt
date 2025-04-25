package logic

import model.InvalidMealNumberException
import model.MealItem
import model.NoMealsFoundException
import utils.Messages.MEALS_NUMBER_CANNOT_BE_NEGATIVE
import utils.Messages.NO_MEALS_WITH_POTATO_FOUND
import utils.Strings.POTATO

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

        if (potatoMeals.isEmpty()) {
            throw NoMealsFoundException(NO_MEALS_WITH_POTATO_FOUND)
        }
        return potatoMeals
            .take(randomMealsCount)
    }

    private fun hasPotato(meal: MealItem, mealName: String): Boolean {
        return meal.ingredients.any { it.contains(mealName, ignoreCase = true) }

    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
    }

}