package logic

import model.MealItem

class GetRandomMealsWithPotatoUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()
    private val potatoKeyword = "potato"

    fun getPotatoMeals(randomMealNumber:Int=10): List<MealItem> {
        return meals
            .filter { meal -> meal.ingredients.any { it.contains(potatoKeyword, ignoreCase = true) } }
            .shuffled()
            .take(randomMealNumber)
    }
}
