package logic

import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import utils.Utils.printMealDetails
import kotlin.math.abs

class GetGymGoerMealsUseCase(dataSource: FoodChangeModeDataSource) {
    private val mealsList = dataSource.getAllMeals()
    fun getGymGoerMeals(
        calories: Double,
        protein: Double,
        caloriesTolerance: Int = 50,
        proteinTolerance: Int = 15
    ): List<MealItem> {
        return mealsList.filter { meal ->
            val calorieDiff = abs(meal.nutrition.calories - calories)
            val proteinDiff = abs(meal.nutrition.protein - protein)
            calorieDiff <= caloriesTolerance && proteinDiff <= proteinTolerance
        }.take(10)
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val getGymGoerMealsUseCase = GetGymGoerMealsUseCase(dataSource)
    println("Gym Helper: Enter your desired meal parameters")
    print("Target Calories: ")
    val targetCalories = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Target Protein (grams): ")
    val targetProtein = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    if (targetCalories <= 0.0 || targetProtein <= 0.0) {
        println("Please enter valid positive numbers for calories and protein.")
        return
    }
    val suggestions = getGymGoerMealsUseCase.getGymGoerMeals(calories = targetCalories, protein = targetProtein)

    if (suggestions.isEmpty()) {
        println("No meals found matching your criteria (within ±50 calories and protein). Try adjusting your targets.")
    } else {
        println("\nMeal Suggestions for ~$targetCalories calories and ~$targetProtein grams of protein:")
        suggestions.forEachIndexed { index, meal ->
            println("*\n Meal number ${index + 1} *")
            printMealDetails(meal)
        }
    }
}