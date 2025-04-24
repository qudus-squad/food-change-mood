package logic

import model.MealItem


class GetHealthyFastMealsUseCase(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun getHealthyFastMeals(
        limitForMinutesHealthyMeal: Int = HEALTHY_MEAL_MINIMUM_PREPARATION_TIME,
        scoredMealsNumber: Int = MAXIMUM_MEALS_TO_SELECT
    ): List<MealItem> {

        val validMeals = meals.filter { meal ->
            meal.minutes <= limitForMinutesHealthyMeal
        }
        if (validMeals.isEmpty()) {

            return emptyList()
        }
        val thresholdPercentile = THRESHOLD_PERCENTILE
        val threshold = maxOf(1, (validMeals.size * thresholdPercentile).toInt())

        val sortedByTotalFat = validMeals.sortedBy { it.nutrition.totalFat }
        val sortedBySaturatedFat = validMeals.sortedBy { it.nutrition.saturatedFat }
        val sortedByCarbohydrates = validMeals.sortedBy { it.nutrition.carbohydrates }

        val lowestTotalFat = sortedByTotalFat.take(threshold).toSet()
        val lowestSaturatedFat = sortedBySaturatedFat.take(threshold).toSet()
        val lowestCarbohydrates = sortedByCarbohydrates.take(threshold).toSet()

        val scoredMeals = validMeals.map { meal ->
            var score = INITIAL_SCORE_VALUE
            if (meal in lowestTotalFat) score += ONE_SCORE_POINT
            if (meal in lowestSaturatedFat) score += ONE_SCORE_POINT
            if (meal in lowestCarbohydrates) score += ONE_SCORE_POINT
            meal to score
        }

        val maxScore = scoredMeals.maxOfOrNull { it.second } ?: INITIAL_SCORE_VALUE
        return scoredMeals.filter { it.second == maxScore && it.second >= 2 }.map { it.first }
            .sortedBy { it.nutrition.totalFat + it.nutrition.saturatedFat + it.nutrition.carbohydrates }
            .take(scoredMealsNumber)
    }

    companion object {
        private const val HEALTHY_MEAL_MINIMUM_PREPARATION_TIME = 15
        private const val MAXIMUM_MEALS_TO_SELECT = 5
        private const val THRESHOLD_PERCENTILE = 0.2
        private const val INITIAL_SCORE_VALUE = 0
        private const val ONE_SCORE_POINT = 1
    }
}