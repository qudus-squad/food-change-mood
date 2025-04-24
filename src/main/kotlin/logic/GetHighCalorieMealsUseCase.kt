package logic

import model.MealItem
import utils.Messages.NO_MORE_HIGH_CALORIE_MEALS

class GetHighCalorieMealsUseCase(private val dataSource: FoodChangeModeDataSource) {
    private val suggestedMealIds = mutableSetOf<Int>() // why used a SET, no iterations and it is one unique value
    // rename selected calorie
    fun suggestMeal(selectedCalorie: Double = DEFAULT_CALORIES_AMOUNT): MealItem {
        val availableMeals = dataSource.getAllMeals()
            .filter { it.nutrition.calories >= selectedCalorie }
            .filterNot { it.id in suggestedMealIds } // filter is too heavy, try to solve it in one filter
        // split this filters into private function and call it

        if (availableMeals.isEmpty()) {
            throw NoSuchElementException(NO_MORE_HIGH_CALORIE_MEALS)
        }
        val suggestedMeal = availableMeals.random()
        suggestedMealIds.add(suggestedMeal.id)
        return suggestedMeal
    }

    companion object {
        private const val DEFAULT_CALORIES_AMOUNT = 700.0
    }
}
