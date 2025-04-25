package logic

import model.MealItem

class GetHighCalorieMealsUseCase(private val dataSource: FoodChangeModeDataSource) {
    private val suggestedMealIds = mutableSetOf<Int>()

    fun suggestMeal(selectedCalorie: Double = DEFAULT_CALORIES_AMOUNT): MealItem {
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

    companion object {
        private const val DEFAULT_CALORIES_AMOUNT = 700.0
        const val NO_MORE_HIGH_CALORIE_MEALS = "No more high-calorie meals available to suggest."

    }
}
