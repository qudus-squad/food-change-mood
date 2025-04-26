package logic

import model.MealItem

class GetSweetsWithNoEggsUseCase(private val dataSource: FoodChangeModeDataSource) {

    private fun getAllSweets(): List<MealItem> {
        return dataSource.getAllMeals().filter { sweet ->
            SWEETS_KEYWORDS in sweet.mealTags
                    && EGGS_KEYWORDS !in sweet.ingredients
        }
    }

    fun suggestSweetsWithNoEgg(randomMealsNumber: Int = MAXIMUM_MEALS_TO_SELECT): List<MealItem> {
        val suggestedRandomSweets = getAllSweets()
            .take(randomMealsNumber)
        if (suggestedRandomSweets.isEmpty()) {
            return emptyList()
        }
        return suggestedRandomSweets
    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
        const val EGGS_KEYWORDS = "eggs"
        const val SWEETS_KEYWORDS = "sweet"
    }
}