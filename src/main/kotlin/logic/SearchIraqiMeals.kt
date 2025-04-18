package logic
import model.MealItem

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
