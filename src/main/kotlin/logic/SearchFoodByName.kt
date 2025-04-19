package logic


class FoodSearchAlgorithm(dataSource: FoodChangeModeDataSource) {
    private val searchAlgorithm = SearchAlgorithm()
    private val mealsList = dataSource.getAllMeals()

    fun searchFoodFromDataSource(searchInput: String): List<Pair<Int, String>> {
        val bestMatches = mutableListOf<Pair<Int, String>>()
        val maxDistance = 3
        val minimumSimilarity = 60
        mealsList.forEach { meal ->
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
        return bestMatches
    }
}

