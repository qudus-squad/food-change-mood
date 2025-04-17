package logic

import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import model.SeafoodMealItem
import utils.ListUtils.orThrowIfEmpty

class SeafoodMealUseCase(private val dataSource: FoodMenu) {

    // TODO Need Implement from dataSource
    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            tags = listOf("60-minutes-or-less", "vegetarian", "mexican"),
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
            description = "Spicy or sweet squash recipe from Mexico.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 31490,
            name = "shrimp and crab salad",
            minutes = 30,
            contributorId = 26278,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("30-minutes-or-less", "seafood"),
            nutrition = Nutrition(
                calories = 173.4,
                totalFat = 18.0,
                sugar = 0.0,
                sodium = 17.0,
                protein = 22.0,
                saturatedFat = 35.0,
                carbohydrates = 1.0
            ),
            stepNumbers = 5,
            steps = listOf(
                "mix ingredients",
                "chill in fridge",
                "serve cold"
            ),
            description = "A refreshing seafood salad with shrimp and crab.",
            ingredients = listOf("shrimp", "crab", "mayonnaise", "lemon"),
            ingredientNumbers = 4
        )
    )

    fun getSeafoodMeals(): List<SeafoodMealItem> {
        return meals
            .filter { isSeafoodMeal(it) }
            .orThrowIfEmpty { NoSeafoodMealsFoundException("No seafood meals found") }
            .sortedByDescending { it.nutrition.protein }
            // Converts meals to SeafoodMealItem with ranking based on protein order.
            .mapIndexed { index, meal ->
                SeafoodMealItem(
                    rank = index + 1,
                    name = meal.name,
                    protein = meal.nutrition.protein
                )
            }

    }


    // Checks if the meal contains any seafood-related ingredient based on known keywords.
    private fun isSeafoodMeal(meal: MealItem): Boolean {
        val seafoodKeywords = listOf(
            "fish", "shrimp", "crab", "lobster", "salmon", "tuna", "clam", "oyster", "scallop", "squid"
        )
        return meal.ingredients.any { ingredient ->
            seafoodKeywords.any { keyword ->
                ingredient.lowercase().contains(keyword)
            }
        }
    }

}

class NoSeafoodMealsFoundException(message: String) : Exception(message)