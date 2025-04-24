package logic

import model.InvalidCountryNameException
import model.MealItem
import model.NoMealsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.INVALID_COUNTRY_NAME
import utils.Messages.NO_MEALS_FOND_FOR_COUNTRY

class GetRandomMealsByCountryNameUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getRandomMealsByCountry(countryName: String, randomMealsNumber: Int): List<MealItem> {

        return dataSource.getAllMeals()
            .filter { isMealRelatedToCountryName(it, formatCountryName(countryName)) }
            .orThrowIfEmpty { NoMealsFoundException("$NO_MEALS_FOND_FOR_COUNTRY $countryName") } // no exception
            .shuffled() // don't use shuffle and use .take(randomnumber)
            .take(randomMealsNumber)
    }
    // use formating in a different use case
    private fun formatCountryName(countryName: String): String {
        return countryName
            .takeIf { it.isNotBlank() && it.matches(Regex("[a-zA-Z\\s]+")) } // annoyed him :(, previous note
            ?.trim()
            ?: throw InvalidCountryNameException(INVALID_COUNTRY_NAME)
    }

    private fun isMealRelatedToCountryName(meal: MealItem, country: String): Boolean {
        return meal.tags.any { it.contains(country, ignoreCase = true) }
                || meal.description.contains(country, ignoreCase = true)
    }
}