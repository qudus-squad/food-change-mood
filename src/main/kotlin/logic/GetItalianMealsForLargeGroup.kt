package logic

import model.MealItem
import model.Nutrition
import data.DataSource
import kotlinx.datetime.LocalDate

class GetItalianMealsForLargeGroup(private val dataSource: DataSource = DataSource()) {

private val meals: List<MealItem> = listOf(
        MealItem(
            id = 200002,
            name = "Tiramisu Tray for Groups",
            minutes = 30,
            contributorId = 20002,
            submitted = LocalDate.parse("2022-01-10"),
            tags = listOf("for-large-groups", "italian", "dessert", "no-cook", "coffee"),
            nutrition = Nutrition(
                calories = 300.0,
                totalFat = 20.0,
                sugar = 25.0,
                sodium = 100.0,
                protein = 5.0,
                saturatedFat = 12.0,
                carbohydrates = 30.0
            ),
            stepNumbers = 4,
            steps = listOf(
                "Mix mascarpone with sugar and cream",
                "Dip ladyfingers in coffee",
                "Layer cream and ladyfingers in a tray",
                "Chill and dust with cocoa powder"
            ),
            description = "Creamy and coffee-infused Italian dessert, ideal for sharing with friends.",
            ingredients = listOf("mascarpone", "ladyfingers", "coffee", "sugar", "cream", "cocoa powder"),
            ingredientNumbers = 6
        ),
        MealItem(
            id = 200001,
            name = "Spaghetti Bolognese for a Crowd",
            minutes = 40,
            contributorId = 20001,
            submitted = LocalDate.parse("2021-03-15"),
            tags = listOf("for-large-groups", "italian", "pasta", "main-dish", "meat", "dinner"),
            nutrition = Nutrition(
                calories = 350.0,
                totalFat = 15.0,
                sugar = 6.0,
                sodium = 500.0,
                protein = 20.0,
                saturatedFat = 5.0,
                carbohydrates = 40.0
            ),
            stepNumbers = 5,
            steps = listOf(
                "Cook spaghetti in salted water",
                "Brown ground beef with onions and garlic",
                "Add tomato sauce and simmer",
                "Mix pasta with sauce",
                "Serve with parmesan"
            ),
            description = "A hearty Italian classic with rich meat sauce, perfect for a large gathering.",
            ingredients = listOf("spaghetti", "ground beef", "tomato sauce", "onion", "garlic", "parmesan cheese"),
            ingredientNumbers = 6
        ),
        MealItem(
            id = 200003,
            name = "Caprese Salad Platter",
            minutes = 15,
            contributorId = 20003,
            submitted = LocalDate.parse("2023-05-20"),
            tags = listOf("for-large-groups", "italian", "salad", "vegetarian", "no-cook", "appetizer"),
            nutrition = Nutrition(
                calories = 150.0,
                totalFat = 10.0,
                sugar = 2.0,
                sodium = 200.0,
                protein = 8.0,
                saturatedFat = 5.0,
                carbohydrates = 5.0
            ),
            stepNumbers = 3,
            steps = listOf(
                "Slice tomatoes and mozzarella",
                "Arrange with basil leaves on a platter",
                "Drizzle with olive oil and balsamic"
            ),
            description = "Fresh and simple Italian salad, perfect for a large group appetizer.",
            ingredients = listOf("tomatoes", "mozzarella", "basil", "olive oil", "balsamic vinegar"),
            ingredientNumbers = 5
        ),
        MealItem(
            id = 200004,
            name = "Pesto Pasta for Groups",
            minutes = 25,
            contributorId = 20004,
            submitted = LocalDate.parse("2020-08-12"),
            tags = listOf("for-large-groups", "italian", "pasta", "vegetarian", "main-dish", "easy"),
            nutrition = Nutrition(
                calories = 280.0,
                totalFat = 12.0,
                sugar = 3.0,
                sodium = 250.0,
                protein = 10.0,
                saturatedFat = 3.0,
                carbohydrates = 35.0
            ),
            stepNumbers = 4,
            steps = listOf(
                "Cook pasta in salted water",
                "Blend basil, pine nuts, garlic, and parmesan for pesto",
                "Toss pasta with pesto sauce",
                "Serve warm or cold"
            ),
            description = "Vibrant and flavorful Italian pasta dish, great for feeding a crowd.",
            ingredients = listOf("pasta", "basil", "pine nuts", "garlic", "parmesan cheese", "olive oil"),
            ingredientNumbers = 6
        )
    )

fun getSuggestedMeals(): List<MealItem> {
        val italianMeals = dataSource.filterMealsByCountry(meals, "italian")
        return italianMeals.filter { meal ->
            "for-large-groups" in meal.tags.map { it.lowercase() }
        }
    }
}

fun main() {
    val mealFetcher = GetItalianMealsForLargeGroup()
    val suggestedMeals = mealFetcher.getSuggestedMeals()

    println("Recommended Italian Dishes for Large Gatherings:")
    for (meal in suggestedMeals) {
        println("- ${meal.name}: ${meal.description}")
    }
}