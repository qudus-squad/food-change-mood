package logic.validation_use_cases

import logic.GetMealsSuggestionUseCase.GetMealsSuggestionInputs
import model.InvalidCountOfIngredientsInput
import model.InvalidCountOfPreparationStepsInput
import model.InvalidCountOfSuggestionsInput
import model.InvalidPreparationTimeInput

 class ValidInputForGetMealsSuggestion {
    fun isValidInputSuggestMeals(
        getMealsSuggestionInputs: GetMealsSuggestionInputs
    ) {
        if (getMealsSuggestionInputs.countOfSuggestions <= 0) throw InvalidCountOfSuggestionsInput(
            NUMBER_OF_SUGGESTIONS
        )

        if (getMealsSuggestionInputs.preparationTime <= 0) throw InvalidPreparationTimeInput(
            INVALID_PREPARATION_TIME
        )

        if (getMealsSuggestionInputs.countOfIngredients <= 0) throw InvalidCountOfIngredientsInput(
            NUMBER_OF_INGREDIENTS
        )

        if (getMealsSuggestionInputs.countOfPreparationSteps <= 0) throw InvalidCountOfPreparationStepsInput(
            NUMBER_OF_PREPARATION_STEPS
        )
    }

    companion object {
        const val NUMBER_OF_SUGGESTIONS = "Invalid number of suggestions : must be greater than 0"
        const val INVALID_PREPARATION_TIME = "Invalid meal preparation time: must be greater than 0"
        const val NUMBER_OF_INGREDIENTS = "Invalid number of ingredients: cannot be negative"
        const val NUMBER_OF_PREPARATION_STEPS = "Invalid number of preparation steps: cannot be negative"
    }
}