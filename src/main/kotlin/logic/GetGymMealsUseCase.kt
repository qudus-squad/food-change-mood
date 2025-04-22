package logic

import model.MealItem
import kotlin.math.abs

class GetMealsForGymUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()
    fun getMealsForGym(
        calories: Double,
        protein: Double,
        numberOfMeals: Int = 10,
        caloriesTolerance: Int = 50,
        proteinTolerance: Int = 15
    ): List<MealItem> {
        return meals.filter { meal ->
            val calorieDiff = abs(meal.nutrition.calories - calories)
            val proteinDiff = abs(meal.nutrition.protein - protein)
            calorieDiff <= caloriesTolerance && proteinDiff <= proteinTolerance
        }   .shuffled()
            .take(numberOfMeals)
    }
}