package logic

import model.MealItem

class GetHealthyFastMealsUseCase(dataSource: FoodChangeModeDataSource) {

    private val mealsList = dataSource.getAllMeals()

    fun getHealthyFastMeals(): List<MealItem> {

        val validMeals = mealsList.filter { meal ->
            meal.minutes <= 15
        }
        if (validMeals.isEmpty()) {

            return emptyList()
        }
        val thresholdPercentile = 0.2
        val threshold = maxOf(1, (validMeals.size * thresholdPercentile).toInt())

        val sortedByTotalFat = validMeals.sortedBy { it.nutrition.totalFat }
        val sortedBySaturatedFat = validMeals.sortedBy { it.nutrition.saturatedFat }
        val sortedByCarbohydrates = validMeals.sortedBy { it.nutrition.carbohydrates }

        val lowestTotalFat = sortedByTotalFat.take(threshold).toSet()
        val lowestSaturatedFat = sortedBySaturatedFat.take(threshold).toSet()
        val lowestCarbohydrates = sortedByCarbohydrates.take(threshold).toSet()

        val scoredMeals = validMeals.map { meal ->
            var score = 0
            if (meal in lowestTotalFat) score += 1
            if (meal in lowestSaturatedFat) score += 1
            if (meal in lowestCarbohydrates) score += 1
            meal to score
        }

        val maxScore = scoredMeals.maxOfOrNull { it.second } ?: 0
        return scoredMeals
            .filter { it.second == maxScore && it.second >= 2 }
            .map { it.first }
            .sortedBy { it.nutrition.totalFat + it.nutrition.saturatedFat + it.nutrition.carbohydrates }
            .take(5)
    }
}