package logic

import model.InvalidMealNumberException
import model.MealItem
import model.NoMealsFoundException
import utils.Messages.MEALS_NUMBER_CANNOT_BE_NEGATIVE
import utils.Messages.NO_MEALS_WITH_POTATO_FOUND

class GetRandomMealsWithPotatoUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getPotatoMeals(RandomMealNumber: Int = 10, NameForMeal:String="potato"): List<MealItem> {
        if (RandomMealNumber < 0) {
            throw InvalidMealNumberException(MEALS_NUMBER_CANNOT_BE_NEGATIVE)
        }
        val potatoMeals = dataSource.getAllMeals()
            .filter { meal ->
                meal.ingredients.any { it.contains(NameForMeal, ignoreCase = true) }
            }

        if (potatoMeals.isEmpty()) {
            throw NoMealsFoundException(NO_MEALS_WITH_POTATO_FOUND)
        }
        return potatoMeals
            .shuffled()
            .take(RandomMealNumber)
    }

}
