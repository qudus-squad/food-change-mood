package logic

import model.InvalidNameMealException
import model.MealItem

class SearchMealsByNameUseCase(private val dataSource: FoodChangeModeDataSource) {
    private val searchAlgorithm = SearchAlgorithm()

    fun searchMealsByName(searchInput: String): List<Pair<Int, String>> {

        if (searchInput.isEmpty()) {
            throw InvalidNameMealException(INVALID_MEAL_NAME)
        }
        val finalMatchingMeals = mutableListOf<Pair<Int, String>>()
        dataSource.getAllMeals().forEach { meal ->
            if (meal.name.isNotEmpty()) {
                val matchingMeals = matchMeal(meal, searchInput)
                finalMatchingMeals.addAll(matchingMeals)
            }
        }

        return finalMatchingMeals
    }

    private fun matchMeal(
        meal: MealItem,
        searchInput: String,
    ): List<Pair<Int, String>> {
        val matchingMeals = mutableListOf<Pair<Int, String>>()

        if (searchAlgorithm.knuthMorris(meal.name, searchInput)) {
            matchingMeals.add(Pair(meal.id, meal.name))
        } else {
            val distance = searchAlgorithm.levenshteinDistance(meal.name.lowercase(), searchInput.lowercase())
            val similarity = searchAlgorithm.calculateMatching(distance, meal.name.length, searchInput.length)
            if (distance <= MAXIMUM_DISTANCE && similarity >= MINIMUM_SIMILARITY) {
                matchingMeals.add(Pair(meal.id, meal.name))
            }
        }

        return matchingMeals
    }

    companion object {
        private const val MAXIMUM_DISTANCE = 3
        private const val MINIMUM_SIMILARITY = 30
        const val INVALID_MEAL_NAME = "name is empty or contains invalid characters"

    }
}