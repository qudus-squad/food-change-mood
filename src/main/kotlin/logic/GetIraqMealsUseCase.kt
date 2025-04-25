package logic

import model.InvalidCountryNameException
import model.MealItem

class GetIraqMealsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getIraqMeals(countryName: String = IRAQ): List<MealItem> {
        if (countryName.isEmpty()) throw InvalidCountryNameException(INVALID_COUNTRY_NAME)

        val iraqMeals = dataSource.getAllMeals().filter { meal -> isIraqMeals(meal, countryName) }
        return iraqMeals
    }

    fun isIraqMeals(meal: MealItem, country: String): Boolean {
        return meal.description.contains(
            country, ignoreCase = true
        )
    }

    companion object {
        const val IRAQ = "iraq"
        const val INVALID_COUNTRY_NAME = "Country name is empty or contains invalid characters"
    }
}
