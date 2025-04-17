package logic

import data.DataSource
import model.MealItem
import model.Nutrition
import kotlinx.datetime.LocalDate

class SearchFoodsbyAddDate(private val dataSource: FoodMenu) {

    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
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
            submitted = LocalDate.parse("2002-06-19"),
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
        ),
        MealItem(
            id = 314090,
            name = "aaaaaaaaaaaaaaaaaaaa",
            minutes = 30,
            contributorId = 26278,
            submitted = LocalDate.parse("2010-06-10"),
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

    fun getSearchMealsByAddDate(date : String): List<MealItem> {
        try {
            val convertDate = convertDate(date)
            val meals = meals
                .filter { it.submitted == convertDate }

            if (meals.isEmpty()){
                throw Exception("No meals found for the date: $date")
            }
            return meals
        }catch (e: Exception) {
            println("Error: ${e.message}")
            return emptyList()
        }
    }
}

private fun convertDate(dateString : String) : LocalDate {
    try {
        return LocalDate.parse(dateString)
    }catch (e : Exception){
        throw Exception("Invalid date format")
    }
}

fun main() {
    val dataSource: FoodMenu = DataSource()
    val searchFoodsbyAddDate = SearchFoodsbyAddDate(dataSource)
    println("search .... ")
    val result: List<MealItem> = searchFoodsbyAddDate.getSearchMealsByAddDate("2002-06-100")
    println(result)
}