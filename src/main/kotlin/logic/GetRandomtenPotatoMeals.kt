package logic

import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class GetRandomTenPotatoMeals(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()

    fun getPotatoMeals(): List<MealItem> {
        return meals
            .filter { meal -> meal.ingredients.any { it.contains("potato", ignoreCase = true) } }
            .shuffled()
            .take(10)
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val potatoMeals = GetRandomTenPotatoMeals(dataSource)
    println("The best potato meals for you:")
    val randomPotatoMeals = potatoMeals.getPotatoMeals()
    randomPotatoMeals.forEach { meal ->
        println("Name: ${meal.name}, Description: ${meal.description}, Preparation Time: ${meal.minutes} minutes")
    }
}