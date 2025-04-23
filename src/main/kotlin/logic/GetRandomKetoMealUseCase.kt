package logic

import model.MealItem
import model.NoMealsFoundException
import utils.Messages.NO_MEALS_FOR_KETO_DIET

class GetRandomKetoMealUseCase(private val dataSource: FoodChangeModeDataSource) {

    private val suggestedMealIds = mutableSetOf<Int>()

    fun getRandomKetoMeal(): MealItem {
        val availableMeals = dataSource.getAllMeals()
            .filter { meal ->
                isKetoFriendly(meal)
            }
            .filterNot { it.id in suggestedMealIds }

        if (availableMeals.isEmpty()) {
            throw NoMealsFoundException(NO_MEALS_FOR_KETO_DIET)
        }

        val suggestedMeal = availableMeals.random()
        suggestedMealIds.add(suggestedMeal.id)
        return suggestedMeal
    }

    private fun isKetoFriendly(meal: MealItem): Boolean {

        val carbohydrates = meal.nutrition.carbohydrates
        val totalFat = meal.nutrition.totalFat
        val protein = meal.nutrition.protein
        val fatToProteinCarbRatio = totalFat / (protein + carbohydrates)

        return carbohydrates < MAX_CARBS && fatToProteinCarbRatio >= MIN_FAT_TO_PROTEIN_RATIO
    }

    companion object {
        const val MAX_CARBS = 10.0
        const val MIN_FAT_TO_PROTEIN_RATIO = 2.0
    }
}

