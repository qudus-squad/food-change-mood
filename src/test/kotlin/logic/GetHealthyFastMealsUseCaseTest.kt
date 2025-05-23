package logic

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetHealthyFastMealsUseCaseTest {

    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var useCase: GetHealthyFastMealsUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk()
    }

    private fun getMealsItem() = listOf(
        MealItem(
            id = 1,
            name = "Meal 1",
            preparationTimeInMinutes = 15,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
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
            preparationTimeInMinutes = 15,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 1.0,
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
            id = 3,
            name = "Meal 3",
            preparationTimeInMinutes = 15,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 10.0,
                carbohydrates = 1.0,
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
            id = 4,
            name = "Meal 4",
            preparationTimeInMinutes = 16,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 5.0,
                carbohydrates = 30.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 5,
            name = "Meal 5",
            preparationTimeInMinutes = 17,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 5.0,
                carbohydrates = 30.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 6,
            name = "Meal 6",
            preparationTimeInMinutes = 18,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 5.0,
                carbohydrates = 30.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 7,
            name = "Meal 7",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 5.0,
                carbohydrates = 30.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 8,
            name = "Meal 8",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 9.0,
                saturatedFat = 4.5,
                carbohydrates = 31.0,
                calories = 8.5,
                sugar = 7.5,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 9,
            name = "Meal 9",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 12.0,
                saturatedFat = 5.5,
                carbohydrates = 31.0,
                calories = 12.0,
                sugar = 6.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 10,
            name = "Meal 10",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 9.0,
                saturatedFat = 5.5,
                carbohydrates = 32.0,
                calories = 11.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 11,
            name = "Meal 11",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 10.0,
                saturatedFat = 5.0,
                carbohydrates = 30.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 12,
            name = "Meal 12",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 15.0,
                saturatedFat = 4.0,
                carbohydrates = 25.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        ),
        MealItem(
            id = 13,
            name = "Meal 13",
            preparationTimeInMinutes = 10,
            contributorId = 100,
            submitted = LocalDate(2025, 4, 1),
            mealTags = listOf("test", "slow"),
            nutrition = Nutrition(
                totalFat = 1.0,
                saturatedFat = 5.0,
                carbohydrates = 20.0,
                calories = 10.0,
                sugar = 10.0,
                sodium = 10.0,
                protein = 10.0
            ),
            stepNumbers = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Takes too long",
            ingredients = listOf("Ingredient1", "Ingredient2"),
            ingredientNumbers = 2
        )
    )

    @Test
    fun `should return empty list when validMeal is empty`() {
        // given
        every { dataSource.getAllMeals() } returns emptyList()
        useCase = GetHealthyFastMealsUseCase(dataSource)
        // when
        val result = useCase.getHealthyFastMeals()
        // then
        result.shouldBeEmpty()
    }

    @Test
    fun `should returns empty list when no meal meets at least 2 nutrient criteria`() {

        // Given
        every { dataSource.getAllMeals() } returns getMealsItem().filter { meal ->
            meal.nutrition.totalFat == 1.0 || meal.nutrition.saturatedFat == 1.0 || meal.nutrition.carbohydrates == 1.0
        }
        val useCase = GetHealthyFastMealsUseCase(dataSource)

        // When
        val result = useCase.getHealthyFastMeals()

        // Then
        result.shouldBeEmpty()
    }

    @Test
    fun `should return empty list when all meals exceed 15 minutes preparation time`() {

        // Given
        every { dataSource.getAllMeals() } returns getMealsItem().filter { time -> time.preparationTimeInMinutes > 15 }
        useCase = GetHealthyFastMealsUseCase(dataSource)

        // When
        val result = useCase.getHealthyFastMeals()

        // Then
        result.shouldBeEmpty()
    }

    @Test
    fun `should return top 5 scoring meals when more than five are eligible`() {

        // Given
        every { dataSource.getAllMeals() } returns getMealsItem().filter { meal -> meal.id > 6 }
        useCase = GetHealthyFastMealsUseCase(dataSource)
        // When
        val result = useCase.getHealthyFastMeals()
        // Then
        result.shouldHaveSize(5)
        result.map { meal -> meal.name }.shouldContainExactlyInAnyOrder(
            "Meal 8", "Meal 11", "Meal 7", "Meal 10", "Meal 13"
        )
    }
}
