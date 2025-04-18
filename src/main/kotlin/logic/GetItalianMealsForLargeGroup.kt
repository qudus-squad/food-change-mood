package logic

import model.MealItem

class GetItalianMealsForLargeGroup(dataSource: FoodChangeModeDataSource) {

    private val meals: List<MealItem> = dataSource.getAllMeals()

    fun getSuggestedMeals(): List<MealItem> {
        val italianMeals = filterMealsByCountry(meals)
        return italianMeals.filter { meal ->
            meal.tags.contains("for-large-groups")
        }
    }

    private fun filterMealsByCountry(mealItems: List<MealItem>): List<MealItem> {
        return mealItems.filter { meal ->
            meal.description.contains("italian", ignoreCase = true) || meal.name.contains("italian", ignoreCase = true)
        }
    }
}