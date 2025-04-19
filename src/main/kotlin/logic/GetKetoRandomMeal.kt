package logic

import model.MealItem

class GetKetoRandomMeal(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    private val recentKetoMealIds = mutableSetOf<Int>()

    fun getRandomKetoMealUseCase(): MealItem? {
        val ketoMeals = meals.filter { isKetoFriendly(it) }
        if (ketoMeals.isEmpty()) return null

        val availableKetoMeals = ketoMeals.filter { it.id !in recentKetoMealIds }
        val finalKetoMeals = if (availableKetoMeals.isEmpty()) {
            recentKetoMealIds.clear()
            ketoMeals
        } else {
            availableKetoMeals
        }

        val selectedMeal = finalKetoMeals.random()
        recentKetoMealIds.add(selectedMeal.id)

        if (recentKetoMealIds.size > ketoMeals.size / 2) {
            recentKetoMealIds.remove(recentKetoMealIds.first())
        }

        return selectedMeal
    }

    private fun isKetoFriendly(meal: MealItem): Boolean {
        val netCarbs = meal.nutrition.carbohydrates
        val fat = meal.nutrition.totalFat
        val protein = meal.nutrition.protein

        val fatToProteinCarbRatio = fat / (protein + netCarbs)

        return netCarbs < 10 && fatToProteinCarbRatio >= 2.0
    }
}