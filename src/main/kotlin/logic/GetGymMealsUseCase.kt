package logic

import model.MealItem
import kotlin.math.abs
// GetGymMeals instead of GetMealsForGymUseCase
class GetMealsForGymUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getMealsForGym(
        calories: Double,
        protein: Double,
        numberOfMeals: Int = 10, // use companion objects & constants
        caloriesTolerance: Int = 50,
        proteinTolerance: Int = 15
    ): List<MealItem> {
        // extract this part in another private function and call it inside the filter with a descriptive name
        return dataSource.getAllMeals().filter { meal ->
            val calorieDiff = abs(meal.nutrition.calories - calories)
            val proteinDiff = abs(meal.nutrition.protein - protein)
            calorieDiff <= caloriesTolerance && proteinDiff <= proteinTolerance
        }   .shuffled() // don't use shuffle, instead use random number generation and plugin it into .take()
            .take(numberOfMeals)
    }
}