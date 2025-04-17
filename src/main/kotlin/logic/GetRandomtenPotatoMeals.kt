package logic

import data.DataSource
import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition

class GetRandomTenPotatoMeals (private val dataSource: FoodMenu = DataSource()) {
    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 1,
            name = "Cheesy Potato Bake",
            minutes = 45,
            contributorId = 101,
            submitted = LocalDate.parse("2021-04-10"),
            tags = listOf("dinner", "comfort food", "american"),
            nutrition = Nutrition(300.0, 15.0, 3.0, 450.0, 8.0, 7.0, 30.0),
            stepNumbers = 5,
            steps = listOf("Preheat oven", "Mix ingredients", "Bake until golden"),
            description = "Classic cheesy potato bake from the US.",
            ingredients = listOf("potatoes", "cheddar cheese", "milk", "butter"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 2,
            name = "Spicy Potato Tacos",
            minutes = 25,
            contributorId = 102,
            submitted = LocalDate.parse("2022-03-08"),
            tags = listOf("mexican", "vegetarian"),
            nutrition = Nutrition(210.0, 7.0, 2.0, 380.0, 5.0, 2.0, 26.0),
            stepNumbers = 4,
            steps = listOf("Cook potatoes", "Warm tortillas", "Assemble tacos"),
            description = "Mexican tacos filled with spicy potatoes.",
            ingredients = listOf("potatoes", "taco shells", "spices"),
            ingredientNumbers = 3
        ),
        MealItem(
            id = 3,
            name = "Sweet Potato Soup",
            minutes = 35,
            contributorId = 103,
            submitted = LocalDate.parse("2021-11-02"),
            tags = listOf("soup", "healthy", "vegetarian"),
            nutrition = Nutrition(150.0, 4.0, 6.0, 300.0, 3.0, 1.0, 22.0),
            stepNumbers = 6,
            steps = listOf("Boil sweet potatoes", "Blend with spices", "Simmer"),
            description = "A creamy soup made with sweet potatoes.",
            ingredients = listOf("sweet potatoes", "onion", "garlic"),
            ingredientNumbers = 3
        ),
        MealItem(
            id = 4,
            name = "Loaded Potato Skins",
            minutes = 40,
            contributorId = 201,
            submitted = LocalDate.parse("2020-10-10"),
            tags = listOf("appetizer", "american"),
            nutrition = Nutrition(250.0, 10.0, 2.0, 420.0, 6.0, 4.0, 24.0),
            stepNumbers = 5,
            steps = listOf("Bake potatoes", "Scoop insides", "Add toppings", "Bake again"),
            description = "Crispy potato skins with cheese and bacon.",
            ingredients = listOf("potatoes", "cheddar cheese", "bacon", "sour cream"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 5,
            name = "Mashed Potatoes",
            minutes = 20,
            contributorId = 202,
            submitted = LocalDate.parse("2019-06-21"),
            tags = listOf("side dish", "classic"),
            nutrition = Nutrition(180.0, 8.0, 1.0, 300.0, 3.0, 3.0, 20.0),
            stepNumbers = 3,
            steps = listOf("Boil potatoes", "Mash with milk", "Serve hot"),
            description = "Classic mashed potatoes.",
            ingredients = listOf("potatoes", "milk", "butter", "salt"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 6,
            name = "Potato Salad",
            minutes = 25,
            contributorId = 203,
            submitted = LocalDate.parse("2020-07-15"),
            tags = listOf("salad", "cold dish"),
            nutrition = Nutrition(220.0, 9.0, 3.0, 340.0, 4.0, 2.0, 23.0),
            stepNumbers = 4,
            steps = listOf("Boil potatoes", "Mix with mayo and spices", "Chill before serving"),
            description = "Creamy potato salad for picnics.",
            ingredients = listOf("potatoes", "mayonnaise", "mustard", "onion"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 7,
            name = "Curried Potatoes",
            minutes = 30,
            contributorId = 204,
            submitted = LocalDate.parse("2018-12-09"),
            tags = listOf("indian", "spicy"),
            nutrition = Nutrition(190.0, 7.0, 2.0, 310.0, 4.0, 1.5, 25.0),
            stepNumbers = 4,
            steps = listOf("Fry spices", "Add potatoes", "Cook until tender"),
            description = "Indian-style curried potatoes.",
            ingredients = listOf("potatoes", "curry powder", "oil", "onion"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 8,
            name = "Potato Gratin",
            minutes = 60,
            contributorId = 205,
            submitted = LocalDate.parse("2017-03-22"),
            tags = listOf("french", "cheesy"),
            nutrition = Nutrition(310.0, 12.0, 1.5, 460.0, 7.0, 6.0, 28.0),
            stepNumbers = 5,
            steps = listOf("Slice potatoes", "Layer with cream", "Bake until golden"),
            description = "French-style baked potato dish.",
            ingredients = listOf("potatoes", "cream", "cheese", "garlic"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 9,
            name = "Breakfast Hash",
            minutes = 20,
            contributorId = 206,
            submitted = LocalDate.parse("2023-01-05"),
            tags = listOf("breakfast", "quick"),
            nutrition = Nutrition(270.0, 11.0, 1.0, 400.0, 6.0, 3.0, 27.0),
            stepNumbers = 4,
            steps = listOf("Chop potatoes", "Fry with veggies", "Add eggs if desired"),
            description = "Quick breakfast hash with potatoes and vegetables.",
            ingredients = listOf("potatoes", "bell peppers", "onion", "eggs"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 10,
            name = "Potato Pancakes",
            minutes = 25,
            contributorId = 207,
            submitted = LocalDate.parse("2021-02-13"),
            tags = listOf("german", "snack"),
            nutrition = Nutrition(230.0, 9.0, 1.0, 380.0, 4.0, 2.5, 24.0),
            stepNumbers = 4,
            steps = listOf("Grate potatoes", "Mix with flour", "Pan-fry"),
            description = "Crispy potato pancakes, German-style.",
            ingredients = listOf("potatoes", "flour", "egg", "onion"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 11,
            name = "Potato Croquettes",
            minutes = 30,
            contributorId = 208,
            submitted = LocalDate.parse("2019-08-28"),
            tags = listOf("fried", "snack"),
            nutrition = Nutrition(280.0, 13.0, 2.0, 390.0, 5.0, 3.0, 26.0),
            stepNumbers = 4,
            steps = listOf("Mash potatoes", "Shape into balls", "Fry"),
            description = "Fried croquettes made with mashed potatoes.",
            ingredients = listOf("potatoes", "breadcrumbs", "egg", "butter"),
            ingredientNumbers = 4
        ),
        MealItem(
            id = 12,
            name = "Shepherd’s Pie",
            minutes = 50,
            contributorId = 209,
            submitted = LocalDate.parse("2022-05-10"),
            tags = listOf("british", "classic"),
            nutrition = Nutrition(330.0, 14.0, 3.0, 450.0, 10.0, 4.0, 29.0),
            stepNumbers = 6,
            steps = listOf("Cook beef", "Top with mashed potatoes", "Bake"),
            description = "British shepherd's pie with a layer of mashed potatoes.",
            ingredients = listOf("ground beef", "mashed potatoes", "onion", "carrots"),
            ingredientNumbers = 4
        )
    )

fun getPotatoMeals(): List<MealItem> {
        return meals
            .filter { meal -> meal.ingredients.any { it.contains("potato", ignoreCase = true) } }
            .shuffled()
            .take(10)
    }
}

fun main() {
    val potatoMeals = GetRandomTenPotatoMeals(DataSource())

    println("The best potato meals for you:")
    val randomPotatoMeals = potatoMeals.getPotatoMeals()
    randomPotatoMeals.forEach { meal ->
        println("Name: ${meal.name}, Description: ${meal.description}, Preparation Time: ${meal.minutes} minutes")
    }
}