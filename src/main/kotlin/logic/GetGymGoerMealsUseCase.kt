package logic

import data.DataSource
import model.MealItem
import model.Nutrition
import utils.Utils.printMealDetails
import kotlin.math.abs

class GetGymGoerMealsUseCase(private val dataSource: FoodMenu = DataSource()) {
    private val mealsList = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 15,
            contributorId = 47892,
            submitted = "2005-09-16",
            tags = listOf(
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
                "preheat oven to 425 degrees f",
                "press dough into the bottom and sides of a 12 inch pizza pan",
                "bake for 5 minutes until set but not browned",
                "cut sausage into small pieces",
                "whisk eggs and milk in a bowl until frothy",
                "spoon sausage over baked crust and sprinkle with cheese",
                "pour egg mixture slowly over sausage and cheese",
                "s& p to taste",
                "bake 15-20 minutes or until eggs are set and crust is brown"
            ),
            description = "This recipe calls for the crust to be prebaked a bit before adding ingredients. Feel free to change sausage to ham or bacon. This warms well in the microwave for those late risers.",
            ingredients = listOf(
                "prepared pizza crust", "sausage patty", "eggs", "milk", "salt and pepper", "cheese"
            ),
            ingredientNumbers = 6
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
                "mexican",
                "chicken",
                "easy",
                "dinner",
                "spicy",
                "meat",
                "southwest"
            ),
            nutrition = Nutrition(
                calories = 119.0,
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
            id = 45678,
            name = "classic spaghetti carbonara",
            minutes = 25,
            contributorId = 56789,
            submitted = "2008-11-15",
            tags = listOf(
                "30-minutes-or-less",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "italian",
                "pasta",
                "easy",
                "quick"
            ),
            nutrition = Nutrition(
                calories = 320.0,
                totalFat = 14.0,
                sugar = 2.0,
                sodium = 400.0,
                protein = 15.0,
                saturatedFat = 6.0,
                carbohydrates = 38.0
            ),
            stepNumbers = 7,
            steps = listOf(
                "cook spaghetti according to package instructions",
                "in a bowl, whisk eggs and grated parmesan cheese",
                "in a pan, cook pancetta until crispy",
                "drain pasta and reserve some cooking water",
                "mix pasta with egg mixture, adding a little pasta water to thicken sauce",
                "add pancetta and toss until well combined",
                "season with black pepper and serve immediately"
            ),
            description = "A rich and creamy Italian carbonara recipe made with eggs, pancetta, and Parmesan. Ready in just 25 minutes!",
            ingredients = listOf("spaghetti", "pancetta", "eggs", "parmesan cheese", "black pepper", "olive oil"),
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
                calories = 150.0,
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
    fun getGymGoerMeals(
        calories: Double,
        protein: Double,
        caloriesTolerance: Int = 50,
        proteinTolerance: Int = 15
    ): List<MealItem> {
        return mealsList.filter { meal ->
            val calorieDiff = abs(meal.nutrition.calories - calories)
            val proteinDiff = abs(meal.nutrition.protein - protein)
            calorieDiff <= caloriesTolerance && proteinDiff <= proteinTolerance
        }
    }
}

fun main() {
    val getGymGoerMealsUseCase = GetGymGoerMealsUseCase()
    println("Gym Helper: Enter your desired meal parameters")
    print("Target Calories: ")
    val targetCalories = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Target Protein (grams): ")
    val targetProtein = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    if (targetCalories <= 0.0 || targetProtein <= 0.0) {
        println("Please enter valid positive numbers for calories and protein.")
        return
    }
    val suggestions = getGymGoerMealsUseCase.getGymGoerMeals(calories = targetCalories, protein = targetProtein)

    if (suggestions.isEmpty()) {
        println("No meals found matching your criteria (within ±50 calories and protein). Try adjusting your targets.")
    } else {
        println("\nMeal Suggestions for ~$targetCalories calories and ~$targetProtein grams of protein:")
        suggestions.forEachIndexed { index, meal ->
            println("* Meal number ${index + 1} *")
            printMealDetails(meal)
        }
    }
}