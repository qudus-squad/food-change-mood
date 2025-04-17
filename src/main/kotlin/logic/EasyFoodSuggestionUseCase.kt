package logic

import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition

class EasyFoodSuggestionUseCase(private val dataSource: FoodMenu) {

    // TODO Need Implement from Meal Repo
    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
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
            submitted = LocalDate.parse("2002-06-17"),
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

        return meals.filter {
            getIsEasyMeal(
                meal = it,
                preparationTime = 30,
                numOfIngredients = 5,
                numOfPreparationSteps = 6
            )
        }.shuffled().take(numberOfSuggest)

    }

    private fun getIsEasyMeal(
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
