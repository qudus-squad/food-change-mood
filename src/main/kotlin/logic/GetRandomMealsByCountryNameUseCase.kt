package logic

import model.InvalidCountryNameException
import model.MealItem
import model.NoMealsFoundException
import utils.ListUtils.orThrowIfEmpty

class GetRandomMealsByCountryNameUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getRandomMealsByCountry(countryName: String, randomMealsNumber: Int): List<MealItem> {

        return dataSource.getAllMeals()
            .filter { isMealRelatedToCountryName(it, formatCountryName(countryName)) }
            .orThrowIfEmpty { NoMealsFoundException("$NO_MEALS_FOND_FOR_COUNTRY $countryName") }
            .take(randomMealsNumber)
    }

    private fun isMealRelatedToCountryName(meal: MealItem, country: String): Boolean {
        return meal.tags.any { tag ->
            tag.contains(country, ignoreCase = true)
        }
                || meal.description.contains(country, ignoreCase = true)
    }

    private fun formatCountryName(countryName: String): String {
        return countryName
            .takeIf { name ->
                name.isNotBlank() &&
                        name.all { char -> char.isLetter() || char.isWhitespace() }
            }
            ?.trim()
            ?: throw InvalidCountryNameException(INVALID_COUNTRY_NAME)
    }

    companion object {
        const val INVALID_COUNTRY_NAME = "Country name is empty or contains invalid characters"
        const val NO_MEALS_FOND_FOR_COUNTRY = "No meals found for country: "
    }
}