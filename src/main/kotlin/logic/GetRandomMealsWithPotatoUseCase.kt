package logic

import model.InvalidMealNumberException
import model.MealItem
import model.NoMealsFoundException
import utils.Messages.MEALS_NUMBER_CANNOT_BE_NEGATIVE
import utils.Messages.NO_MEALS_WITH_POTATO_FOUND
import utils.Strings.POTATO


class GetRandomMealsWithPotatoUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getPotatoMeals(randomMealNumber: Int = 10, nameForMeal: String = POTATO): List<MealItem> {
        if (randomMealNumber < 0) {
            throw InvalidMealNumberException(MEALS_NUMBER_CANNOT_BE_NEGATIVE)
        }
        val potatoMeals = dataSource.getAllMeals()
            .filter { meal ->
                meal.ingredients.any { it.contains(nameForMeal, ignoreCase = true) }
            }

        if (potatoMeals.isEmpty()) {
            throw NoMealsFoundException(NO_MEALS_WITH_POTATO_FOUND)
        }
        return potatoMeals
            .shuffled()
            .take(randomMealNumber)
    }

}
