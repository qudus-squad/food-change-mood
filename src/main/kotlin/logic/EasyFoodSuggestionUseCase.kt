package logic

import model.MealItem
import model.Nutrition

class EasyFoodSuggestionUseCase(private val mealRepository: MealRepository) {

    // TODO Need Implement from Meal Repo
    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 55,
            contributorId = 47892,
            submitted = "2005-09-16",
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 31490,
            name = "a bit different breakfast pizza",
            minutes = 30,
            contributorId = 26278,
            submitted = "2002-06-17",
            tags = listOf("30-minutes-or-less", "breakfast"),
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
            description = "Breakfast pizza that can be reheated.",
            ingredients = listOf("prepared pizza crust", "sausage"),
            ingredientNumbers = 2
        )
    )

    fun suggestEasyMeals(numberOfSuggest: Int): List<MealItem> {

        return meals.filter { getIsEasyMeal(it) }.shuffled().take(numberOfSuggest)

    }

    private fun getIsEasyMeal(meal: MealItem): Boolean {
        return meal.minutes <= PREPARATION_TIME &&
                meal.ingredients.size <= NUMBER_OF_INGREDIENTS &&
                meal.steps.size <= NUMBER_PREPARATION_STEPS
    }

    companion object {
        const val PREPARATION_TIME = 30
        const val NUMBER_OF_INGREDIENTS = 5
        const val NUMBER_PREPARATION_STEPS = 6
    }
}
