package logic

import logic.validation_use_cases.ValidInputForGetMealsSuggestion
import model.MealItem

class GetMealsSuggestionUseCase(
    private val dataSource: FoodChangeModeDataSource, private val validInputForGymMeals: ValidInputForGetMealsSuggestion
) {

    fun suggestEasyMeals(
        getMealsSuggestionInputs: GetMealsSuggestionInputs
    ): List<MealItem> {

        validInputForGymMeals.isValidInputSuggestMeals(
            getMealsSuggestionInputs
        )

        return dataSource.getAllMeals().filter { meal ->
            isEasyMeal(
                meal = meal, getMealsSuggestionInputs = getMealsSuggestionInputs
            )
        }.take(getMealsSuggestionInputs.countOfSuggestions)

    }

    private fun isEasyMeal(
        meal: MealItem, getMealsSuggestionInputs: GetMealsSuggestionInputs
    ): Boolean {
        return  meal.preparationTimeInMinutes <= getMealsSuggestionInputs.preparationTime &&
                meal.ingredientNumbers <= getMealsSuggestionInputs.countOfIngredients &&
                meal.stepNumbers <= getMealsSuggestionInputs.countOfPreparationSteps
    }

    companion object {
        private const val DEFAULT_NUMBER_OF_SUGGESTIONS = 10
        private const val DEFAULT_PREPARATION_TIME = 30
        private const val DEFAULT_NUMBER_OF_INGREDIENTS = 5
        private const val DEFAULT_NUMBER_OF_PREPARATION_STEPS = 6

    }

    data class GetMealsSuggestionInputs(
        val countOfSuggestions: Int = DEFAULT_NUMBER_OF_SUGGESTIONS,
        val preparationTime: Int = DEFAULT_PREPARATION_TIME,
        val countOfIngredients: Int = DEFAULT_NUMBER_OF_INGREDIENTS,
        val countOfPreparationSteps: Int = DEFAULT_NUMBER_OF_PREPARATION_STEPS,
    )
}