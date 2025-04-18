package logic

import di.appModule
import kotlinx.datetime.LocalDate
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class SearchFoodByAddDate(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun getSearchMealsByAddDate(date : String): List<MealItem> {
        try {
            val convertDate = convertDate(date)
            val meals = meals
                .filter { it.submitted == convertDate }

            if (meals.isEmpty()){
                throw Exception("No meals found for the date: $date")
            }
            return meals
        }catch (e: Exception) {
            println("Error: ${e.message}")
            return emptyList()
        }
    }
}

private fun convertDate(dateString : String) : LocalDate {
    try {
        return LocalDate.parse(dateString)
    }catch (e : Exception){
        throw Exception("Invalid date format")
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val searchFoodByAddDate = SearchFoodByAddDate(dataSource)
    println("search .... ")
    val result: List<MealItem> = searchFoodByAddDate.getSearchMealsByAddDate("2010-09-20")
    println(result)
}