package logic

import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import utils.ListUtils.orThrowIfEmpty


class CountryFoodUseCase(private val dataSource: FoodChangeModeDataSource) {

    // TODO Need Implement from dataSource
    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            tags = listOf("60-minutes-or-less", "vegetarian", "mexican"),
            nutrition = Nutrition(
                calories = 51.5,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 2.0,
                saturatedFat = 0.0,
                carbohydrates = 4.0
            ),
            stepNumbers = 11,
            steps = listOf(
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe from Mexico.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 31490,
            name = "a bit different breakfast pizza",
            minutes = 30,
            contributorId = 26278,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("30-minutes-or-less", "breakfast", "italian"),
            nutrition = Nutrition(
                calories = 173.4,
                totalFat = 18.0,
                sugar = 0.0,
                sodium = 17.0,
                protein = 22.0,
                saturatedFat = 35.0,
                carbohydrates = 1.0
            ),
            stepNumbers = 9,
            steps = listOf(
                "preheat oven",
                "press dough",
                "bake crust"
            ),
            description = "Breakfast pizza that can be reheated, inspired by Italian cuisine.",
            ingredients = listOf("prepared pizza crust", "sausage"),
            ingredientNumbers = 2
        )
    )

    // get randomly ordered meals related to that country.
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

    // Checks whether a meal is associated with a specific country based on tags or description.
    private fun isCountryRelated(meal: MealItem, country: String): Boolean {

        return meal.tags.any { it.contains(country, ignoreCase = true) }
                || meal.description.contains(country, ignoreCase = true)

    }
}

class InvalidCountryException(message: String) : Exception(message)

class NoMealsFoundException(message: String) : Exception(message)
