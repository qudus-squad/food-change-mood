package logic

import model.MealItem

class GetSweetsWithNoEggsUseCase(dataSource: FoodChangeModeDataSource) {
    private val sweetsKeywords  = "sweets"
    private val eggsKeywords = "eggs"
    private val allSweets = dataSource.getAllMeals().filter { mealItem -> sweetsKeywords in mealItem.tags  && eggsKeywords !in mealItem.ingredients }
    private val suggestedSweetIds = mutableSetOf<Int>()

    fun suggestSweetsWithNoEgg(): MealItem? {
        val availableSweets = allSweets.filter { it.id !in suggestedSweetIds }
        if (availableSweets.isEmpty()) {
            return null
        }
        val suggestedSweet = availableSweets.random()
        suggestedSweetIds.add(suggestedSweet.id)
        return suggestedSweet
    }
}