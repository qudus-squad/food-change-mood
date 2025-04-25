package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GetRandomKetoMealUseCaseTest {
    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var getRandomKetoMealUseCase: GetRandomKetoMealUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        getRandomKetoMealUseCase = GetRandomKetoMealUseCase(dataSource)
    }

    private fun getMeals() = listOf(
        MealItem(
            id = 1,
            name = "Keto Salmon Salad",
            preparationTimeInMinutes = 20,
            contributorId = 101,
            submitted = LocalDate.parse("2023-01-15"),
            mealTags = listOf("Keto", "Salad", "High Fat", "High Protein"),
            nutrition = Nutrition(
                calories = 450.0,
                totalFat = 70.0,
                sugar = 2.0,
                sodium = 500.0,
                protein = 20.0,
                saturatedFat = 8.0,
                carbohydrates = 5.0
            ),
            stepNumbers = 4,
            steps = listOf("Grill salmon", "Chop veggies", "Toss with olive oil", "Serve"),
            description = "A keto-friendly high-protein, high-fat salmon salad",
            ingredients = listOf("Salmon", "Spinach", "Olive oil"),
            ingredientNumbers = 3
        ), MealItem(
            id = 2,
            name = "Chicken and Rice",
            preparationTimeInMinutes = 30,
            contributorId = 102,
            submitted = LocalDate.parse("2022-07-10"),
            mealTags = listOf("Chicken", "Rice", "Carbs"),
            nutrition = Nutrition(
                calories = 600.0,
                totalFat = 15.0,
                sugar = 3.0,
                sodium = 700.0,
                protein = 35.0,
                saturatedFat = 5.0,
                carbohydrates = 45.0
            ),
            stepNumbers = 5,
            steps = listOf("Cook rice", "Grill chicken", "Mix with vegetables", "Combine rice and chicken", "Serve"),
            description = "A simple and hearty chicken and rice meal",
            ingredients = listOf("Chicken breast", "Rice", "Olive oil"),
            ingredientNumbers = 3
        ), MealItem(
            id = 3,
            name = "Grilled Chicken with Vegetables",
            preparationTimeInMinutes = 25,
            contributorId = 103,
            submitted = LocalDate.parse("2024-06-20"),
            mealTags = listOf("Chicken", "Grilled", "Low Fat"),
            nutrition = Nutrition(
                calories = 350.0,
                totalFat = 15.0,
                sugar = 2.0,
                sodium = 500.0,
                protein = 40.0,
                saturatedFat = 4.0,
                carbohydrates = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Grill chicken", "Sauté vegetables", "Serve with herbs"),
            description = "A simple grilled chicken with vegetables meal",
            ingredients = listOf("Chicken breast", "Carrots", "Broccoli"),
            ingredientNumbers = 3
        )
    )

    @Test
    fun `should return keto friendly meal when available`() {
        // Given
        every { dataSource.getAllMeals() } returns getMeals()

        // When
        val result = getRandomKetoMealUseCase.getRandomKetoMeal()

        // Then
        result?.name shouldBe "Keto Salmon Salad"

    }

    @Test
    fun `should return null when no keto meals available`() {
        // Given
        every { dataSource.getAllMeals() } returns getMeals().map {
            it.copy(
                nutrition = Nutrition(
                    calories = 450.0,
                    totalFat = 40.0,
                    sugar = 2.0,
                    sodium = 500.0,
                    protein = 20.0,
                    saturatedFat = 8.0,
                    carbohydrates = 5.0
                )
            )
        }

        // When
        val result = getRandomKetoMealUseCase.getRandomKetoMeal()
        // Then
        result.shouldBeNull()

    }

    @Test
    fun `should return null when carbohydrates greater that ten`() {
        // Given
        every { dataSource.getAllMeals() } returns listOf(getMeals()[1])

        // When
        val result = getRandomKetoMealUseCase.getRandomKetoMeal()
        // Then
        result.shouldBeNull()

    }

    @Test
    fun `should return null when fatToProteinCarbRatio less than two`() {
        // Given
        every { dataSource.getAllMeals() } returns listOf(getMeals()[2])

        // When
        val result = getRandomKetoMealUseCase.getRandomKetoMeal()
        // Then
        result.shouldBeNull()

    }
}