package logic

import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

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
            // Assuming we check if the meal is Italian by looking for keywords in the description or name
            meal.description.contains("italian", ignoreCase = true) || meal.name.contains("italian", ignoreCase = true)
        }
    }

}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val mealFetcher = GetItalianMealsForLargeGroup(dataSource)
    val suggestedMeals = mealFetcher.getSuggestedMeals()

    println("Recommended Italian Dishes for Large Gatherings:")
    for (meal in suggestedMeals) {
        println("- ${meal.name}: ${meal.description}")
    }
}