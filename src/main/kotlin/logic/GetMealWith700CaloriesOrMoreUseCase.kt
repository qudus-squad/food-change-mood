package logic

import data.DataSource
import model.MealItem
import model.Nutrition
import utils.Utils.printMealDetails


class GetMealWith700CaloriesOrMoreUseCase(private val data: FoodMenu) {

    private val mealsList = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 15,
            contributorId = 47892,
            submitted = "2005-09-16",
            tags = listOf(
                "fast-food",
                "60-minutes-or-less",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "north-american",
                "side-dishes",
                "vegetables",
                "mexican",
                "easy",
                "fall",
                "holiday-event",
                "vegetarian",
                "winter",
                "dietary",
                "christmas",
                "seasonal",
                "squash"
            ),
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
                "make a choice and proceed with recipe",
                "depending on size of squash, cut into half or fourths",
                "remove seeds",
                "for spicy squash, drizzle olive oil or melted butter over each cut squash piece",
                "season with mexican seasoning mix ii",
                "for sweet squash, drizzle melted honey, butter, grated piloncillo over each cut squash piece",
                "season with sweet mexican spice mix",
                "bake at 350 degrees, again depending on size, for 40 minutes up to an hour, until a fork can easily pierce the skin",
                "be careful not to burn the squash especially if you opt to use sugar or butter",
                "if you feel more comfortable, cover the squash with aluminum foil the first half hour, give or take, of baking",
                "if desired, season with salt"
            ),
            description = "Autumn is my favorite time of year to cook! This recipe can be prepared either spicy or sweet, your choice! Two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
            ingredients = listOf(
                "winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"
            ),
            ingredientNumbers = 7
        ), MealItem(
            id = 89012,
            name = "spicy chicken fajitas",
            minutes = 12,
            contributorId = 10293,
            submitted = "2011-08-23",
            tags = listOf(
                "60-minutes-or-less",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "fast-food",
                "mexican",
                "chicken",
                "easy",
                "dinner",
                "spicy",
                "meat",
                "southwest"
            ),
            nutrition = Nutrition(
                calories = 800.0,
                totalFat = 9.0,
                sugar = 3.0,
                sodium = 600.0,
                protein = 25.0,
                saturatedFat = 3.0,
                carbohydrates = 14.0
            ),
            stepNumbers = 8,
            steps = listOf(
                "slice chicken into strips",
                "season with fajita seasoning",
                "heat oil in a pan",
                "cook chicken for 5-7 minutes until browned",
                "add sliced bell peppers and onions",
                "cook for an additional 5-7 minutes until vegetables are tender",
                "serve with tortillas, sour cream, and salsa",
                "garnish with cilantro if desired"
            ),
            description = "A spicy and flavorful chicken fajita recipe perfect for a quick dinner. Serve with tortillas and your favorite toppings!",
            ingredients = listOf(
                "chicken breast",
                "fajita seasoning",
                "bell peppers",
                "onions",
                "tortillas",
                "sour cream",
                "salsa",
                "cilantro"
            ),
            ingredientNumbers = 8
        ), MealItem(
            id = 31490,
            name = "a bit different breakfast pizza",
            minutes = 13,
            contributorId = 26278,
            submitted = "2002-06-17",
            tags = listOf(
                "30-minutes-or-less",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "fast-food",
                "north-american",
                "breakfast",
                "main-dish",
                "pork",
                "american",
                "oven",
                "easy",
                "kid-friendly",
                "pizza",
                "dietary",
                "northeastern-united-states",
                "meat",
                "equipment"
            ),
            nutrition = Nutrition(
                calories = 760.31,
                totalFat = 18.0,
                sugar = 0.0,
                sodium = 17.0,
                protein = 22.0,
                saturatedFat = 35.0,
                carbohydrates = 1.0
            ),
            stepNumbers = 9,
            steps = listOf(
                "preheat oven to 425 degrees f",
                "press dough into the bottom and sides of a 12 inch pizza pan",
                "bake for 5 minutes until set but not browned",
                "cut sausage into small pieces",
                "whisk eggs and milk in a bowl until frothy",
                "spoon sausage over baked crust and sprinkle with cheese",
                "pour egg mixture slowly over sausage and cheese",
                "bake 15-20 minutes or until eggs are set and crust is brown"
            ),
            description = "This recipe calls for the crust to be prebaked a bit before adding ingredients. Feel free to change sausage to ham or bacon. This warms well in the microwave for those late risers.",
            ingredients = listOf(
                "prepared pizza crust", "sausage patty", "eggs", "milk", "salt and pepper", "cheese"
            ),
            ingredientNumbers = 6
        ), MealItem(
            id = 54321,
            name = "vegetable stir-fry",
            minutes = 30,
            contributorId = 23456,
            submitted = "2015-02-07",
            tags = listOf(
                "30-minutes-or-less",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "vegetarian",
                "asian",
                "easy",
                "healthy",
                "dinner",
                "vegetables"
            ),
            nutrition = Nutrition(
                calories = 700.0,
                totalFat = 5.0,
                sugar = 8.0,
                sodium = 300.0,
                protein = 4.0,
                saturatedFat = 0.5,
                carbohydrates = 23.0
            ),
            stepNumbers = 7,
            steps = listOf(
                "heat oil in a pan",
                "add sliced vegetables (carrots, bell peppers, zucchini, etc.)",
                "stir-fry for 5-7 minutes",
                "add soy sauce and a pinch of salt",
                "stir to coat vegetables evenly",
                "add sesame seeds for garnish",
                "serve over rice"
            ),
            description = "A quick and easy vegetable stir-fry recipe that's healthy and full of flavor. Perfect for a weeknight dinner.",
            ingredients = listOf(
                "carrots", "bell peppers", "zucchini", "soy sauce", "sesame seeds", "rice", "olive oil"
            ),
            ingredientNumbers = 7
        )
    )
    private val suggestedMealIds = mutableSetOf<Int>()

    fun suggestMeal(): MealItem? {

        val availableMeals = mealsList.filter {
            it.nutrition.calories >= 700 && it.id !in suggestedMealIds
        }

        if (availableMeals.isEmpty()) {
            return null
        }

        val suggestedMeal = availableMeals.random()
        suggestedMealIds.add(suggestedMeal.id)
        return suggestedMeal
    }


}

fun main() {

    val dataSource = DataSource()
    val suggester = GetMealWith700CaloriesOrMoreUseCase(dataSource)
    var suggestedMeal = suggester.suggestMeal()

    try {
        while (suggestedMeal != null) {
            println("Suggested Meal: ${suggestedMeal.name} \n${suggestedMeal.description}")
            println("Press 1 for Like or 0 for Dislike")
            val userResponse = readln().toInt()
            when (userResponse) {
                1 -> {
                    printMealDetails(suggestedMeal)
                    break
                }
                0 -> {
                    suggestedMeal = suggester.suggestMeal()
                    if (suggestedMeal == null) {
                        println("No more available meals .")
                    }
                }
                else -> {
                    throw InvalidUserInputException("Invalid Input")
                }
            }
        }
    } catch (e: InvalidUserInputException) {
        println(e.message)
    } catch (e: Exception) {
        println("An unexpected error occurred: ${e.message}")
    }
}

class InvalidUserInputException(message: String) : Exception(message)
