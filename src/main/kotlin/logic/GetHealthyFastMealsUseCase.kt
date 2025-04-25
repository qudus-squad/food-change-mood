package logic

import model.MealItem
import utils.ListUtils.getRandomRangeFromList

class GetHealthyFastMealsUseCase(private val dataSource: FoodChangeModeDataSource) {


    fun getHealthyFastMeals(
        preparationTimeLimit: Int = HEALTHY_MEAL_MINIMUM_PREPARATION_TIME,
        mealsCount: Int = MAXIMUM_MEALS_TO_SELECT
    ): List<MealItem> {

        val filteredMeals = dataSource.getAllMeals().filter { meal ->
            meal.preparationTimeInMinutes <= preparationTimeLimit
        }
        if (filteredMeals.isEmpty()) {
            return emptyList()
        }
        val thresholdPercentile = THRESHOLD_PERCENTILE
        val threshold = maxOf(1, (filteredMeals.size * thresholdPercentile).toInt())

        val sortedByTotalFat = filteredMeals.sortedBy { it.nutrition.totalFat }
        val sortedBySaturatedFat = filteredMeals.sortedBy { it.nutrition.saturatedFat }
        val sortedByCarbohydrates = filteredMeals.sortedBy { it.nutrition.carbohydrates }

        val lowestTotalFat = sortedByTotalFat.take(threshold).toSet()
        val lowestSaturatedFat = sortedBySaturatedFat.take(threshold).toSet()
        val lowestCarbohydrates = sortedByCarbohydrates.take(threshold).toSet()

        val scoredMeals = filteredMeals.map { meal ->
            var score = INITIAL_SCORE_VALUE
            if (meal in lowestTotalFat) score += ONE_SCORE_POINT
            if (meal in lowestSaturatedFat) score += ONE_SCORE_POINT
            if (meal in lowestCarbohydrates) score += ONE_SCORE_POINT
            meal to score
        }

        val maxScore = scoredMeals.maxOfOrNull { it.second } ?: INITIAL_SCORE_VALUE
        return scoredMeals.filter { it.second == maxScore && it.second >= MAXIMUM_SCORE }.map { it.first }
            .sortedBy { it.nutrition.totalFat + it.nutrition.saturatedFat + it.nutrition.carbohydrates }
            .getRandomRangeFromList(mealsCount)
    }

    companion object {
        private const val HEALTHY_MEAL_MINIMUM_PREPARATION_TIME = 15
        private const val MAXIMUM_MEALS_TO_SELECT = 5
        private const val THRESHOLD_PERCENTILE = 0.2
        private const val INITIAL_SCORE_VALUE = 0
        private const val ONE_SCORE_POINT = 1
        private const val MAXIMUM_SCORE = 2
    }
}
