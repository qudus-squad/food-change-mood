package logic

import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import utils.Utils.printMealDetails

class SweetsWithNoEggs(dataSource: FoodChangeModeDataSource) {
    private val allSweets = dataSource.getAllMeals().filter { mealItem -> "sweet" in mealItem.tags  && "eggs" !in mealItem.ingredients }
    private val suggestedSweetIds = mutableSetOf<Int>()

    fun suggestASweetWithNoEgg(): MealItem? {
        val availableSweets = allSweets.filter { it.id !in suggestedSweetIds }
        if (availableSweets.isEmpty()) {
            return null
        }
        val suggestedSweet = availableSweets.random()
        suggestedSweetIds.add(suggestedSweet.id)
        return suggestedSweet
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val sweetSuggester = SweetsWithNoEggs(dataSource)
    var suggestedSweet = sweetSuggester.suggestASweetWithNoEgg()
    try {
        while (suggestedSweet != null) {
            println("Suggested Egg-Free Sweet: ${suggestedSweet.name} \n${suggestedSweet.description}")
            println("Press 1 for Like or 0 for Dislike")
            val userResponse = readln().toInt()
            when (userResponse) {
                1 -> {
                    printMealDetails(suggestedSweet)
                    break
                }
                0 -> {
                    suggestedSweet = sweetSuggester.suggestASweetWithNoEgg()
                    if (suggestedSweet == null) {
                        println("No more available egg-free sweets.")
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
