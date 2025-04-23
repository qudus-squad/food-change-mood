package logic

import model.InvalidMealNumberException
import model.MealItem
import model.NoMealsFoundException
import utils.Messages.Meal_number_cannot_be_negative
import utils.Messages.No_meals_with_potato_found

class GetRandomMealsWithPotatoUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getPotatoMeals(RandomMealNumber: Int = 10, NameForMeal:String="potato"): List<MealItem> {
        if (RandomMealNumber < 0) {
            throw InvalidMealNumberException(Meal_number_cannot_be_negative)
        }
        val potatoMeals = dataSource.getAllMeals()
            .filter { meal ->
                meal.ingredients.any { it.contains(NameForMeal, ignoreCase = true) }
            }

        if (potatoMeals.isEmpty()) {
            throw NoMealsFoundException(No_meals_with_potato_found)
        }
        return potatoMeals
            .shuffled()
            .take(RandomMealNumber)
    }

}
