package logic

import model.InvalidInputSuggestEasyMealsException
import model.MealItem
import model.NoMealsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.INVALID_PREPARATION_TIME
import utils.Messages.NO_SUGGESTION_MEALS
import utils.Messages.NUMBER_OF_INGREDIENTS
import utils.Messages.NUMBER_OF_PREPARATION_STEPS
import utils.Messages.NUMBER_OF_SUGGESTIONS

class GetMealsSuggestionUseCase(private val dataSource: FoodChangeModeDataSource) {


    fun suggestEasyMeals(
        numberOfSuggestions: Int = 10,
        preparationTime: Int = 30,
        numberOfIngredients: Int = 5,
        numberOfPreparationSteps: Int = 6,
    ): List<MealItem> {

        if (numberOfSuggestions <= 0)
            throw InvalidInputSuggestEasyMealsException(NUMBER_OF_SUGGESTIONS)

        if (preparationTime <= 0)
            throw InvalidInputSuggestEasyMealsException(INVALID_PREPARATION_TIME)

        if (numberOfIngredients < 0)
            throw InvalidInputSuggestEasyMealsException(NUMBER_OF_INGREDIENTS)

        if (numberOfPreparationSteps < 0)
            throw InvalidInputSuggestEasyMealsException(NUMBER_OF_PREPARATION_STEPS)


        return dataSource.getAllMeals().filter { meal ->
            isEasyMeal(
                meal = meal,
                preparationTime,
                numberOfIngredients,
                numberOfPreparationSteps
            )
        }.orThrowIfEmpty {
            NoMealsFoundException(
                "${NO_SUGGESTION_MEALS} " +
                        "for ${preparationTime} " +
                        ", ${numberOfSuggestions} " +
                        ", ${numberOfPreparationSteps}"
            )
        }
            .take(numberOfSuggestions)
    }

    private fun isEasyMeal(
        meal: MealItem,
        preparationTime: Int,
        numberOfIngredients: Int,
        numberOfPreparationSteps: Int,
    ): Boolean {
        return meal.minutes <= preparationTime &&
                meal.ingredientNumbers <= numberOfIngredients &&
                meal.stepNumbers <= numberOfPreparationSteps
    }
}
