package logic

import model.MealItem

class GetMealWith700CaloriesOrMoreUseCase(dataSource: FoodChangeModeDataSource) {
    private val mealsList = dataSource.getAllMeals()
    private val suggestedMealIds = mutableSetOf<Int>()

    fun suggestMeal(): MealItem? {
        val availableMeals = mealsList.filter {
            it.nutrition.calories >= 700 && it.id !in suggestedMealIds
        }
        if (availableMeals.isEmpty()) {
            return null
        }
        val suggestedMeal = availableMeals.random()
        suggestedMealIds.add(suggestedMeal.id)
        return suggestedMeal
    }
}