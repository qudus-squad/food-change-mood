package logic

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetHighCalorieMealsUseCaseTest {
    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        getHighCalorieMealsUseCase = GetHighCalorieMealsUseCase(dataSource)
    }

    private fun getMealItems() = listOf(
        MealItem(
            id = 1,
            name = "Quinoa Salad",
            preparationTimeInMinutes = 15,
            contributorId = 101,
            submitted = LocalDate.parse("2021-03-10"),
            mealTags = listOf("healthy", "vegetarian", "low-calorie"),
            nutrition = Nutrition(
                calories = 350.0,
                totalFat = 10.0,
                sugar = 2.0,
                sodium = 150.0,
                protein = 8.0,
                saturatedFat = 1.0,
                carbohydrates = 45.0
            ),
            stepNumbers = 2,
            steps = listOf("Cook quinoa", "Mix with veggies"),
            description = "Light and refreshing salad.",
            ingredients = listOf("quinoa", "cucumber", "tomato", "olive oil", "lemon juice"),
            ingredientNumbers = 5
        ), MealItem(
            id = 2,
            name = "Steak with Fries",
            preparationTimeInMinutes = 25,
            contributorId = 102,
            submitted = LocalDate.parse("2022-05-18"),
            mealTags = listOf("main", "high-protein", "high-calorie"),
            nutrition = Nutrition(
                calories = 650.0,
                totalFat = 60.0,
                sugar = 1.0,
                sodium = 800.0,
                protein = 45.0,
                saturatedFat = 25.0,
                carbohydrates = 50.0
            ),
            stepNumbers = 3,
            steps = listOf("Grill steak", "Fry potatoes", "Serve hot"),
            description = "Hearty and satisfying meal.",
            ingredients = listOf("steak", "potatoes", "salt", "pepper", "oil"),
            ingredientNumbers = 5
        ), MealItem(
            id = 3,
            name = "Chicken Alfredo Pasta",
            preparationTimeInMinutes = 30,
            contributorId = 103,
            submitted = LocalDate.parse("2020-11-02"),
            mealTags = listOf("pasta", "comfort-food", "high-calorie"),
            nutrition = Nutrition(
                calories = 820.0,
                totalFat = 45.0,
                sugar = 3.0,
                sodium = 700.0,
                protein = 30.0,
                saturatedFat = 18.0,
                carbohydrates = 60.0
            ),
            stepNumbers = 4,
            steps = listOf("Boil pasta", "Cook chicken", "Make Alfredo sauce", "Mix and serve"),
            description = "Creamy and flavorful.",
            ingredients = listOf("pasta", "chicken", "cream", "cheese", "garlic"),
            ingredientNumbers = 5
        )
    )

    @Test
    fun `should return meal when calories are 700 or more`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItems()

        // When
        val result = getHighCalorieMealsUseCase.suggestMeal()

        // Then
        result.map { meal -> meal.name }.shouldContain("Chicken Alfredo Pasta")

    }

    @Test
    fun `should exclude meal when calories are less 700 or more`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItems()

        // When
        val result = getHighCalorieMealsUseCase.suggestMeal()

        // Then
        result.map { meal -> meal.name }.shouldNotContain("Steak with Fries")

    }

    @Test
    fun `should be empty when there no meals with default value `() {
        // Given
        val lowCalorieMeals = getMealItems().filter { meal -> meal.nutrition.calories == 0.0 }
        every { dataSource.getAllMeals() } returns lowCalorieMeals.toList()
        // When & Then
        val result = getHighCalorieMealsUseCase.suggestMeal()
        result.shouldBeEmpty()
    }
}
