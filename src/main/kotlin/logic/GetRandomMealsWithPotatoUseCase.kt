package logic

import model.MealItem

class GetRandomMealsWithPotatoUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    fun getPotatoMeals(RandomMealNumber:Int=10): List<MealItem> {
        return meals
            .filter { meal -> meal.ingredients.any { it.contains("potato", ignoreCase = true) } }
            .shuffled()
            .take(RandomMealNumber)
    }
}
