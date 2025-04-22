package logic

import model.MealItem

class GetRandomMealsWithPotato(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    fun getPotatoMeals(numberForRandomMeal:Int=10): List<MealItem> {
        return meals
            .filter { meal -> meal.ingredients.any { it.contains("potato", ignoreCase = true) } }
            .shuffled()
            .take(numberForRandomMeal)
    }
}
