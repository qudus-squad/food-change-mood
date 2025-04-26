package logic

import logic.validation_use_cases.ValidInputForGetGymMeals
import model.MealItem
import kotlin.math.abs

class GetGymMealsUseCase(
    private val dataSource: FoodChangeModeDataSource, private val validInputForGetGymMeals: ValidInputForGetGymMeals
) {

    fun getMealsForGym(
        getGymMeals: GetMealForGymInputs
    ): List<MealItem> {
        validInputForGetGymMeals.isValidInputForGymMeals(
            getGymMeals
        )
        return dataSource.getAllMeals().filter { meal ->
            isWithinGymRange(meal, getGymMeals)
        }.take(getGymMeals.numberOfMeals)
    }

    private fun isWithinGymRange(
        meal: MealItem, getGymMeals: GetMealForGymInputs
    ): Boolean {
        val calorieDiff = abs(meal.nutrition.calories - getGymMeals.calories)
        val proteinDiff = abs(meal.nutrition.protein - getGymMeals.protein)
        return calorieDiff <= getGymMeals.caloriesTolerance && proteinDiff <= getGymMeals.proteinTolerance
    }

    data class GetMealForGymInputs(
        val calories: Double,
        val protein: Double,
        val numberOfMeals: Int = NUMBER_OF_MEALS,
        val caloriesTolerance: Int = CALORIES_TOLERANCE,
        val proteinTolerance: Int = PROTEIN_TOLERANCE,
    )

    companion object {
        const val NUMBER_OF_MEALS = 10
        const val CALORIES_TOLERANCE = 50
        const val PROTEIN_TOLERANCE = 15
    }
}