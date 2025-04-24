package logic

import model.MealItem
import model.NoSweetsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.NO_EGG_FREE_SWEETS
import utils.Strings.EGGS_KEYWORDS
import utils.Strings.SWEETS_KEYWORDS

class GetSweetsWithNoEggsUseCase(private val dataSource: FoodChangeModeDataSource) {

    // edit the access modifier
    fun getAllSweets():List<MealItem>  { // split the code in an independent private function
        return dataSource.getAllMeals().filter { sweet ->
        SWEETS_KEYWORDS in sweet.tags && EGGS_KEYWORDS !in sweet.ingredients
    }.orThrowIfEmpty { NoSweetsFoundException(NO_EGG_FREE_SWEETS) } // no need for exception
    }
    // rename randomMealsNumber to randomMealsCount
    fun suggestSweetsWithNoEgg(randomMealsNumber: Int = MAXIMUM_MEALS_TO_SELECT): List<MealItem> {
        val suggestedRandomSweets = getAllSweets()
            .shuffled() // don't use shuffle
            .take(randomMealsNumber)
        if (suggestedRandomSweets.isEmpty()) {
             throw NoSweetsFoundException(NO_EGG_FREE_SWEETS) // no need for empty exception
        }
        return suggestedRandomSweets
    }

    companion object {
        private const val MAXIMUM_MEALS_TO_SELECT = 10
    }
}