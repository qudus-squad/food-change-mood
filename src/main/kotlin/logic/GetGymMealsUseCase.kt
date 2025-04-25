package logic

import model.InvalidGetGymMealsException
import model.MealItem
import kotlin.math.abs

class GetGymMealsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getMealsForGym(
        calories: Double,
        protein: Double,
        numberOfMeals: Int = NUMBER_OF_MEALS,
        caloriesTolerance: Int = CALORIES_TOLERANCE,
        proteinTolerance: Int = PROTEIN_TOLERANCE,
    ): List<MealItem> {
        isValidInputForGymMeals(calories, protein, numberOfMeals, caloriesTolerance, proteinTolerance)
        return dataSource.getAllMeals().filter { meal ->
            isWithinGymRange(meal, calories, protein, caloriesTolerance, proteinTolerance)
        }.take(numberOfMeals)
    }

    private fun isValidInputForGymMeals(
        calories: Double, protein: Double, numberOfMeals: Int, caloriesTolerance: Int, proteinTolerance: Int
    ) {
        if (calories <= 0.0) throw InvalidGetGymMealsException(INVALID_CALORIES_TOLERANCE_MEAL)
        if (protein <= 0.0) throw InvalidGetGymMealsException(INVALID_CALORIES_MEAL)
        if (numberOfMeals <= 0) throw InvalidGetGymMealsException(INVALID_MEALS_NUMBER)
        if (caloriesTolerance <= 0) throw InvalidGetGymMealsException(INVALID_PROTEIN_TOLERANCE_MEAL)
        if (proteinTolerance <= 0) throw InvalidGetGymMealsException(INVALID_PROTEIN_MEAL)
    }

    private fun isWithinGymRange(
        meal: MealItem, calories: Double, protein: Double, caloriesTolerance: Int, proteinTolerance: Int
    ): Boolean {
        val calorieDiff = abs(meal.nutrition.calories - calories)
        val proteinDiff = abs(meal.nutrition.protein - protein)
        return calorieDiff <= caloriesTolerance && proteinDiff <= proteinTolerance
    }

    companion object {
        const val NUMBER_OF_MEALS = 10
        const val CALORIES_TOLERANCE = 50
        const val PROTEIN_TOLERANCE = 15
        const val INVALID_CALORIES_TOLERANCE_MEAL = "Invalid calories tolerance for meal"
        const val INVALID_CALORIES_MEAL = "Invalid calories for meal"
        const val INVALID_PROTEIN_TOLERANCE_MEAL = "Invalid protein tolerance for meal"
        const val INVALID_PROTEIN_MEAL = "Invalid protein for meal"
        const val INVALID_MEALS_NUMBER = "Invalid number of meals"
    }
}