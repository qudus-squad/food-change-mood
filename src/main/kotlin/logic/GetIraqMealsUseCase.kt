package logic

import model.InvalidCountryNameException
import model.MealItem
import model.NoMealsFoundException
import utils.Messages.INVALID_COUNTRY_NAME
import utils.Messages.NO_MEALS_FOND_FOR_COUNTRY
import utils.Strings.IRAQ

class GetIraqMealsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getIraqMeals(country: String = IRAQ): List<MealItem> {
        if (country.isEmpty())
            throw InvalidCountryNameException(INVALID_COUNTRY_NAME)
        val iraqMeals = dataSource.getAllMeals().filter {
            it.description.contains(
                country,
                ignoreCase = true
            )
        }
        if (iraqMeals.isEmpty())
            throw NoMealsFoundException(NO_MEALS_FOND_FOR_COUNTRY)
        return iraqMeals
    }
}