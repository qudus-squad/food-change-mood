package logic

import model.MealItem

class GetFoodSuggestionUseCase(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun suggestEasyMeals(
        numberOfSuggestion: Int = 10,
        preparationTime: Int = 30,
        numberOfIngredients: Int = 5,
        numberOfPreparationSteps: Int = 6,
    ): List<MealItem> {
        return meals.filter { meal ->
            isEasyMeal(
                meal = meal,
                preparationTime,
                numberOfIngredients,
                numberOfPreparationSteps
            )
        }
            .shuffled()
            .take(numberOfSuggestion)
    }

    private fun isEasyMeal(
        meal: MealItem,
        preparationTime: Int,
        numberOfIngredients: Int,
        numberOfPreparationSteps: Int,
    ): Boolean {
        return meal.minutes <= preparationTime &&
                meal.ingredients.size <= numberOfIngredients &&
                meal.steps.size <= numberOfPreparationSteps
    }
}
