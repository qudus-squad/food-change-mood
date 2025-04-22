package logic

import model.InvalidCountryException
import model.MealItem
import model.NoMealsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.INVALID_COUNTRY_NAME
import utils.Messages.NO_MEALS_FOND_FOR_COUNTRY

class GetRandomMealsByCountryName(dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun getRandomMealsByCountry(countryName: String, randomMealsNumber: Int): List<MealItem> {

        val formattedCountryName = countryName
            .takeIf { it.isNotBlank() && it.matches(Regex("[a-zA-Z\\s]+")) }
            ?.trim()
            ?.lowercase()
            ?: throw InvalidCountryException(INVALID_COUNTRY_NAME)

        return meals
            .filter { isMealRelatedToCountryName(it, formattedCountryName) }
            .orThrowIfEmpty { NoMealsFoundException("$NO_MEALS_FOND_FOR_COUNTRY $countryName") }
            .shuffled()
            .take(randomMealsNumber)
    }

    private fun isMealRelatedToCountryName(meal: MealItem, country: String): Boolean {

        return meal.tags.any { it.contains(country, ignoreCase = true) }
                || meal.description.contains(country, ignoreCase = true)
    }
}