package logic
import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class GetIraqMealsUsingDescription(private val dataSource: FoodChangeModeDataSource) {

    fun getIraqMeals(): List<MealItem> {
        val iraqMeals = dataSource.getAllMeals().filter {
            it.description.contains(
                "iraqi",
                ignoreCase = true
            ) || it.description.contains("Iraq", ignoreCase = true)
        }

        if (iraqMeals.isEmpty()) return emptyList()

        return iraqMeals
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val iraqMeals = GetIraqMealsUsingDescription(dataSource).getIraqMeals()

    if (iraqMeals.isEmpty()) println("iraqi meals not found")
    else {
        println("iraqi meals found : ")
        iraqMeals.forEach {
            println("${it.name}: ${it.description}")
        }
    }
}
