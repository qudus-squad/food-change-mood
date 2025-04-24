package logic

import model.InvalidMealNumberException
import model.MealItem
import model.NoMealsFoundException
import utils.Messages.MEALS_NUMBER_CANNOT_BE_NEGATIVE
import utils.Messages.NO_MEALS_WITH_POTATO_FOUND
import utils.Strings.POTATO

class GetRandomMealsWithPotatoUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getPotatoMeals(
        randomMealsNumber: Int = MAXIMUM_MEALS_TO_SELECT,
        nameForMeal: String = POTATO
    ): List<MealItem> { // validation in different use case
        if (randomMealsNumber < 0) { // randomMealsCount
            throw InvalidMealNumberException(MEALS_NUMBER_CANNOT_BE_NEGATIVE)
        }
        val potatoMeals = dataSource.getAllMeals()
            .filter { meal ->
                meal.ingredients.any { it.contains(nameForMeal, ignoreCase = true) } // extract it into private function
            }

        if (potatoMeals.isEmpty()) {
            throw NoMealsFoundException(NO_MEALS_WITH_POTATO_FOUND)
        }
        return potatoMeals
            .shuffled() // don't use shuffle, instead use .take(randomNumber)
            .take(randomMealsNumber)
    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
    }

}