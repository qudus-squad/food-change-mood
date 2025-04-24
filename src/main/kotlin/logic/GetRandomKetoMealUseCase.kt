package logic

import model.MealItem
import model.NoMealsFoundException
import utils.Messages.NO_MEALS_FOR_KETO_DIET

class GetRandomKetoMealUseCase(private val dataSource: FoodChangeModeDataSource) {

    private val suggestedMealsIds = mutableSetOf<Int>() // no iteration, you just save it one time

    fun getRandomKetoMeal(): MealItem {
        val availableMeals = dataSource.getAllMeals()
            .filter { meal ->
                isKetoFriendly(meal)
            }
            .filterNot { it.id in suggestedMealsIds } // mix them in a single filter

        if (availableMeals.isEmpty()) {
            throw NoMealsFoundException(NO_MEALS_FOR_KETO_DIET)
        }

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

