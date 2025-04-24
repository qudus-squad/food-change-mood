package logic

import model.MealItem
import model.NoSweetsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.NO_EGG_FREE_SWEETS
import utils.Strings.EGGS_KEYWORDS
import utils.Strings.SWEETS_KEYWORDS

class GetSweetsWithNoEggsUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getAllSweets():List<MealItem>  {
        return dataSource.getAllMeals().filter { sweet ->
        SWEETS_KEYWORDS in sweet.tags && EGGS_KEYWORDS !in sweet.ingredients
    }.orThrowIfEmpty { NoSweetsFoundException(NO_EGG_FREE_SWEETS) }
    }

    fun suggestSweetsWithNoEgg(randomMealsNumber: Int = MAXIMUM_MEALS_TO_SELECT): List<MealItem> {
        val suggestedRandomSweets = getAllSweets()
            .shuffled()
            .take(randomMealsNumber)
        if (suggestedRandomSweets.isEmpty()) {
             throw NoSweetsFoundException(NO_EGG_FREE_SWEETS)
        }
        return suggestedRandomSweets
    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
    }
}