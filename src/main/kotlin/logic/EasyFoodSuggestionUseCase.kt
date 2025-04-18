package logic
import model.MealItem

class EasyFoodSuggestionUseCase(private val dataSource: FoodChangeModeDataSource) {

    private val meals = dataSource.getAllMeals()

    fun getSuggestEasyMeals(numberOfSuggest: Int): List<MealItem> {
        return meals.filter {
            isEasyFood(
                meal = it,
                preparationTime = 30,
                numOfIngredients = 5,
                numOfPreparationSteps = 6
            )
        }.shuffled().take(numberOfSuggest)
    }

    private fun isEasyFood(
        meal: MealItem,
        preparationTime: Int,
        numOfIngredients: Int,
        numOfPreparationSteps: Int,
    ): Boolean {
        return meal.minutes <= preparationTime &&
                meal.ingredients.size <= numOfIngredients &&
                meal.steps.size <= numOfPreparationSteps
    }
}
