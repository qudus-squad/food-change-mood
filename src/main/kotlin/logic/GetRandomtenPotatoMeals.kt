package logic

import model.MealItem

class GetRandomTenPotatoMeals(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    fun getPotatoMeals(): List<MealItem> {
        return meals
            .filter { meal -> meal.ingredients.any { it.contains("potato", ignoreCase = true) } }
            .shuffled()
            .take(10)
    }
}
