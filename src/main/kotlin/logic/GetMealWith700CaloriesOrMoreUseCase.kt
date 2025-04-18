package logic

import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import utils.Utils.printMealDetails


class GetMealWith700CaloriesOrMoreUseCase(dataSource: FoodChangeModeDataSource) {

    private val mealsList = dataSource.getAllMeals()
    private val suggestedMealIds = mutableSetOf<Int>()

    fun suggestMeal(): MealItem? {

        val availableMeals = mealsList.filter {
            it.nutrition.calories >= 700 && it.id !in suggestedMealIds
        }

        if (availableMeals.isEmpty()) {
            return null
        }

        val suggestedMeal = availableMeals.random()
        suggestedMealIds.add(suggestedMeal.id)
        return suggestedMeal
    }


}

fun main() {

    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val suggester = GetMealWith700CaloriesOrMoreUseCase(dataSource)
    var suggestedMeal = suggester.suggestMeal()

    try {
        while (suggestedMeal != null) {
            println("Suggested Meal: ${suggestedMeal.name} \n${suggestedMeal.description}")
            println("Press 1 for Like or 0 for Dislike")
            val userResponse = readln().toInt()
            when (userResponse) {
                1 -> {
                    printMealDetails(suggestedMeal)
                    break
                }
                0 -> {
                    suggestedMeal = suggester.suggestMeal()
                    if (suggestedMeal == null) {
                        println("No more available meals .")
                    }
                }
                else -> {
                    throw InvalidUserInputException("Invalid Input")
                }
            }
        }
    } catch (e: InvalidUserInputException) {
        println(e.message)
    } catch (e: Exception) {
        println("An unexpected error occurred: ${e.message}")
    }
}

class InvalidUserInputException(message: String) : Exception(message)
