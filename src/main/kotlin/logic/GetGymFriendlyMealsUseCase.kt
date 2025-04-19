package logic

import model.MealItem
import kotlin.math.abs

class GetGymFriendlyMealsUseCase(dataSource: FoodChangeModeDataSource) {
    private val mealsList = dataSource.getAllMeals()
    fun findGymFriendlyMealsUseCase(
        calories: Double,
        protein: Double,
        caloriesTolerance: Int = 50,
        proteinTolerance: Int = 15
    ): List<MealItem> {
        return mealsList.filter { meal ->
            val calorieDiff = abs(meal.nutrition.calories - calories)
            val proteinDiff = abs(meal.nutrition.protein - protein)
            calorieDiff <= caloriesTolerance && proteinDiff <= proteinTolerance
        }.take(10)
    }
}
