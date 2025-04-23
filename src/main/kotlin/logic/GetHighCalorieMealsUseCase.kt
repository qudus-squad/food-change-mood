package logic

import model.MealItem
import utils.Messages.NO_MORE_HIGH_CALORIE_MEALS

class GetHighCalorieMealsUseCase(private val dataSource: FoodChangeModeDataSource) {
    private val suggestedMealIds = mutableSetOf<Int>()

    fun suggestMeal(selectedCalorie: Double = 700.0): MealItem {
        val availableMeals = dataSource.getAllMeals()
            .filter { it.nutrition.calories >= selectedCalorie }
            .filterNot { it.id in suggestedMealIds }

        if (availableMeals.isEmpty()) {
            throw NoSuchElementException(NO_MORE_HIGH_CALORIE_MEALS)
        }
        val suggestedMeal = availableMeals.random()
        suggestedMealIds.add(suggestedMeal.id)
        return suggestedMeal
    }
}
