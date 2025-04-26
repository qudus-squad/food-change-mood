package logic

import model.MealItem

class GetRandomKetoMealUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getRandomKetoMeal(): MealItem? {
        val suggestedMealsIds = mutableSetOf<Int>()
        val availableMeals = dataSource.getAllMeals().filter { meal ->
            isKetoFriendly(meal) && meal.id !in suggestedMealsIds
        }

        if (availableMeals.isEmpty()) return null

        val suggestedMeal = availableMeals.random()
        suggestedMealsIds.add(suggestedMeal.id)
        return suggestedMeal
    }

    private fun isKetoFriendly(meal: MealItem): Boolean {
        val totalCarbohydrates = meal.nutrition.carbohydrates
        val totalFat = meal.nutrition.totalFat
        val totalProtein = meal.nutrition.protein
        val fatToProteinCarbRatio = totalFat / (totalProtein + totalCarbohydrates)

        return totalCarbohydrates < MAXIMUM_CARBOHYDRATES && fatToProteinCarbRatio >= MINIMUM_FAT_TO_PROTEIN_RATIO
    }

    companion object {
        private const val MAXIMUM_CARBOHYDRATES = 10.0
        private const val MINIMUM_FAT_TO_PROTEIN_RATIO = 2.0
    }
}
