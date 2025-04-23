package logic

import model.MealItem

class GetMealsSuggestionUseCase(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun suggestEasyMeals(
        numberOfSuggestions: Int = 10,
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
            .take(numberOfSuggestions)
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
