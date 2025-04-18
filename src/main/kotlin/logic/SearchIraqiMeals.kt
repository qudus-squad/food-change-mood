package logic
import model.MealItem

class GetIraqMealsUsingDescription(private val dataSource: FoodChangeModeDataSource) {

    fun getIraqMeals(country: String = "iraq"): List<MealItem> {
        val iraqMeals = dataSource.getAllMeals().filter {
            it.description.contains(
                country,
                ignoreCase = true
            )
        }
        if (iraqMeals.isEmpty()) return emptyList()
        return iraqMeals
    }
}