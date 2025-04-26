package logic.validation_use_cases

import logic.GetGymMealsUseCase
import model.*

class ValidInputForGetGymMeals {
     fun isValidInputForGymMeals(
         getGymMeals :GetGymMealsUseCase.GetMealForGymInputs
    ) {
        if (getGymMeals.calories <= 0.0) throw InvalidCaloriesInput(INVALID_CALORIES_TOLERANCE_MEAL)
        if (getGymMeals.protein <= 0.0) throw InvalidProteinInput(INVALID_CALORIES_MEAL)
        if (getGymMeals.numberOfMeals <= 0) throw InvalidNumberOfMealsInput(INVALID_MEALS_NUMBER)
        if (getGymMeals.caloriesTolerance <= 0) throw InvalidCaloriesToleranceInput(INVALID_PROTEIN_TOLERANCE_MEAL)
        if (getGymMeals.proteinTolerance <= 0) throw InvalidProteinToleranceInput(INVALID_PROTEIN_MEAL)
    }

    companion object {
        const val INVALID_CALORIES_TOLERANCE_MEAL = "Invalid calories tolerance for meal"
        const val INVALID_CALORIES_MEAL = "Invalid calories for meal"
        const val INVALID_PROTEIN_TOLERANCE_MEAL = "Invalid protein tolerance for meal"
        const val INVALID_PROTEIN_MEAL = "Invalid protein for meal"
        const val INVALID_MEALS_NUMBER = "Invalid number of meals"
    }
}