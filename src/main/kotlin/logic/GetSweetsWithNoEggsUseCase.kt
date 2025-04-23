package logic

import model.MealItem
import model.NoSweetsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.NO_EGG_FREE_SWEETS
import utils.Strings.EGGS_KEYWORDS
import utils.Strings.SWEETS_KEYWORDS

class GetSweetsWithNoEggsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getAllSweets() = dataSource.getAllMeals().filter { sweet ->
        SWEETS_KEYWORDS in sweet.tags && EGGS_KEYWORDS !in sweet.ingredients
    }.orThrowIfEmpty { NoSweetsFoundException(NO_EGG_FREE_SWEETS) }

    fun suggestSweetsWithNoEgg(randomMealNumber: Int = 10): List<MealItem> {
        val suggestedRandomSweets = getAllSweets()
            .shuffled()
            .take(randomMealNumber)
        if (suggestedRandomSweets.isEmpty()) {
            return emptyList()
        }
        return suggestedRandomSweets
    }
}