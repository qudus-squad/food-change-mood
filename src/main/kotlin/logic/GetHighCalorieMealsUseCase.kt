package logic
import model.MealItem

class GetHighCalorieMealsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun suggestMeal(calorieLimit: Double = DEFAULT_CALORIES_AMOUNT): List<MealItem> {
        val availableMeals = filterCalorie(calorieLimit)
        if (availableMeals.isEmpty()) return emptyList()
        return listOf((availableMeals.random()))
    }

    private fun filterCalorie(calorieLimit: Double = DEFAULT_CALORIES_AMOUNT): List<MealItem> {
        return dataSource.getAllMeals().filter { meal ->
                meal.nutrition.calories >= calorieLimit
            }
    }

    companion object {
        private const val DEFAULT_CALORIES_AMOUNT = 700.0
    }
}
