package logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GuessGameUseCaseTest {
    private var  mockDataSource = mockk<FoodChangeModeDataSource>()

    private fun getMealItems(): List<MealItem> {
        return listOf(
            MealItem(
                id = 1,
                name = "Meal 1",
                preparationTimeInMinutes = 15,
                contributorId = 100,
                submitted = kotlinx.datetime.LocalDate(2025, 4, 1),
                mealTags = listOf("test", "slow"),
                nutrition = Nutrition(
                    totalFat = 1.0,
                    saturatedFat = 10.0,
                    carbohydrates = 30.0,
                    calories = 0.0,
                    sugar = 0.0,
                    sodium = 0.0,
                    protein = 0.0
                ),
                stepNumbers = 3,
                steps = listOf("Step 1", "Step 2", "Step 3"),
                description = "Takes too long",
                ingredients = listOf("Ingredient1", "Ingredient2"),
                ingredientNumbers = 2
            ),
            MealItem(
                id = 2,
                name = "Meal 2",
                preparationTimeInMinutes = 20,
                contributorId = 101,
                submitted = kotlinx.datetime.LocalDate(2025, 4, 2),
                mealTags = listOf("quick", "vegan"),
                nutrition = Nutrition(
                    totalFat = 2.0,
                    saturatedFat = 0.5,
                    carbohydrates = 25.0,
                    calories = 150.0,
                    sugar = 5.0,
                    sodium = 10.0,
                    protein = 3.0
                ),
                stepNumbers = 2,
                steps = listOf("Prepare ingredients", "Cook"),
                description = "Simple and healthy",
                ingredients = listOf("Vegetable A", "Vegetable B"),
                ingredientNumbers = 2
            )
        )
    }

    @Test
    fun `should not return null when the game starts`() {
        // Given
        val meals = getMealItems()
        every { mockDataSource.getAllMeals() } returns meals
        val gameUseCase = GuessGameUseCase(mockDataSource)
        // When
        val selectedMeal = gameUseCase.selectedMeal
        // Then
        selectedMeal.shouldNotBeNull()
    }

    @Test
    fun `should return false when the user guesses incorrectly three times`() {
        // Given
        val meals = getMealItems()
        every { mockDataSource.getAllMeals() } returns meals
        val gameUseCase = GuessGameUseCase(mockDataSource)
        // When
        val result = gameUseCase.playGuessGame()
        // Then
        result.shouldBeFalse()
    }

    @Test
    fun `should return true when the input guess is true at any attempt`() {
        // Given
        val meals = getMealItems()
        every { mockDataSource.getAllMeals() } returns meals
        val gameUseCase = GuessGameUseCase(mockDataSource)
        every { gameUseCase.getUserGuess() } returns 20
        // When
        val result = gameUseCase.playGuessGame()
        // Then
        result.shouldBeTrue()
    }

    @Test
    fun `should return null when the input is invalid`() {
        // Given
        val gameUseCase = GuessGameUseCase(mockDataSource)
        // When
        every { gameUseCase.getUserGuess() } returns null
        val guess = gameUseCase.getUserGuess()
        // Then
        guess.shouldBeNull()
    }
}