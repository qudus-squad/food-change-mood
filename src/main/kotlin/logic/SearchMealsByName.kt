package logic

import model.InvalidNameMealException
import model.NoMealsFoundException
import utils.Messages.INVALID_MEAL_NAME
import utils.Messages.NO_MEALS_FOUND_FOR_NAME


class SearchMealsByNameUseCase(private val dataSource: FoodChangeModeDataSource) {
    private val searchAlgorithm = SearchAlgorithm()


    fun searchMealsByName(searchInput: String): List<Pair<Int, String>> {
        if (searchInput.isEmpty())
            throw InvalidNameMealException(INVALID_MEAL_NAME)

        val bestMatches = mutableListOf<Pair<Int, String>>()
        val maxDistance = 3
        val minimumSimilarity = 60
        dataSource.getAllMeals().forEach { meal ->
            meal.name
            if (meal.name.isNotEmpty()) {
                if (searchAlgorithm.knuthMorris(meal.name, searchInput)) {
                    bestMatches.add(Pair(meal.id, meal.name))
                } else {
                    val distance =
                        searchAlgorithm.levenshteinDistance(meal.name.lowercase(), searchInput.lowercase())
                    val similarity =
                        searchAlgorithm.calculateMatching(distance, meal.name.length, searchInput.length)
                    if (distance <= maxDistance && similarity >= minimumSimilarity) {
                        bestMatches.add(Pair(meal.id, meal.name))
                    }
                }
            }
        }
        if (bestMatches.isEmpty())
            throw NoMealsFoundException(NO_MEALS_FOUND_FOR_NAME)

        return bestMatches
    }
}

