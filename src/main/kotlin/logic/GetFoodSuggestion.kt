package logic

import model.MealItem

class GetFoodSuggestion(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun suggestEasyMeals(numberOfSuggest: Int): List<MealItem> {
        return meals.filter { meal ->
            isEasyMeal(meal = meal)
        }.shuffled().take(numberOfSuggest)
    }

    private fun isEasyMeal(
        meal: MealItem,
        preparationTime: Int = 30,
        numOfIngredients: Int = 5,
        numOfPreparationSteps: Int = 6,
    ): Boolean {
        return meal.minutes <= preparationTime &&
                meal.ingredients.size <= numOfIngredients &&
                meal.steps.size <= numOfPreparationSteps
    }
}
