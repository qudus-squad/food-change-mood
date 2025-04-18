package logic

import model.MealItem
import utils.ListUtils.orThrowIfEmpty

class CountryFoodUseCase(private val dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun getRandomlyCountryMeals(countryName: String, numOfRandomlyMeals: Int): List<MealItem> {

        val normalizedCountry = countryName
            .takeIf { it.isNotBlank() && it.matches(Regex("[a-zA-Z\\s]+")) }
            ?.trim()
            ?.lowercase()
            ?: throw InvalidCountryException("Country name is empty or contains invalid characters")

        return meals
            .filter { isCountryRelated(it, normalizedCountry) }
            .orThrowIfEmpty { NoMealsFoundException("No meals found for country: $countryName") }
            .shuffled()
            .take(numOfRandomlyMeals)
    }

    private fun isCountryRelated(meal: MealItem, country: String): Boolean {

        return meal.tags.any { it.contains(country, ignoreCase = true) }
                || meal.description.contains(country, ignoreCase = true)
    }
}

class InvalidCountryException(message: String) : Exception(message)

class NoMealsFoundException(message: String) : Exception(message)
