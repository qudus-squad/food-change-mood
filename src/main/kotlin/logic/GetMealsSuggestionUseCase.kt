package logic

import model.InvalidInputSuggestEasyMealsException
import model.MealItem

class GetMealsSuggestionUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun suggestEasyMeals(
        countOfSuggestions: Int = DEFAULT_NUMBER_OF_SUGGESTIONS,
        preparationTime: Int = DEFAULT_PREPARATION_TIME,
        countOfIngredients: Int = DEFAULT_NUMBER_OF_INGREDIENTS,
        countOfPreparationSteps: Int = DEFAULT_NUMBER_OF_PREPARATION_STEPS,
    ): List<MealItem> {

        isValidInputSuggestMeals(
            countOfSuggestions, preparationTime, countOfIngredients, countOfPreparationSteps
        )

        return dataSource.getAllMeals().filter { meal ->
            isEasyMeal(
                meal = meal, preparationTime, countOfIngredients, countOfPreparationSteps
            )
        }.take(countOfSuggestions)
    }

    private fun isEasyMeal(
        meal: MealItem,
        preparationTime: Int,
        numberOfIngredients: Int,
        numberOfPreparationSteps: Int,
    ): Boolean {
        return meal.minutes <= preparationTime && meal.ingredientNumbers <= numberOfIngredients && meal.stepNumbers <= numberOfPreparationSteps
    }


    private fun isValidInputSuggestMeals(
        numberOfSuggestions: Int, preparationTime: Int, numberOfIngredients: Int, numberOfPreparationSteps: Int
    ) {
        if (numberOfSuggestions <= 0) throw InvalidInputSuggestEasyMealsException(NUMBER_OF_SUGGESTIONS)

        if (preparationTime <= 0) throw InvalidInputSuggestEasyMealsException(INVALID_PREPARATION_TIME)

        if (numberOfIngredients < 0) throw InvalidInputSuggestEasyMealsException(NUMBER_OF_INGREDIENTS)

        if (numberOfPreparationSteps < 0) throw InvalidInputSuggestEasyMealsException(NUMBER_OF_PREPARATION_STEPS)

    }

    companion object {
        private const val DEFAULT_NUMBER_OF_SUGGESTIONS = 10
        private const val DEFAULT_PREPARATION_TIME = 30
        private const val DEFAULT_NUMBER_OF_INGREDIENTS = 5
        private const val DEFAULT_NUMBER_OF_PREPARATION_STEPS = 6
        const val NUMBER_OF_SUGGESTIONS = "Invalid number of suggestions : must be greater than 0"
        const val INVALID_PREPARATION_TIME = "Invalid meal preparation time: must be greater than 0"
        const val NUMBER_OF_INGREDIENTS = "Invalid number of ingredients: cannot be negative"
        const val NUMBER_OF_PREPARATION_STEPS = "Invalid number of preparation steps: cannot be negative"
    }
}

