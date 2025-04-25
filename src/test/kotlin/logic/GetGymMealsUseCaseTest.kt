package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.InvalidGetGymMealsException
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetGymMealsUseCaseTest {

    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var getMealsForGymUseCase: GetGymMealsUseCase

    private fun getMealItem() = listOf<MealItem>(
        MealItem(
            id = 13773,
            name = "1",
            preparationTimeInMinutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            mealTags = listOf("60-minutes-or-less", "vegetarian"),
            nutrition = Nutrition(
                calories = 200.0,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 20.0,
                saturatedFat = 0.0,
                carbohydrates = 4.0
            ),
            stepNumbers = 11,
            steps = listOf(
                "make a choice and proceed", "cut into half or fourths", "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        ), MealItem(
            id = 137739,
            name = "2",
            preparationTimeInMinutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            mealTags = listOf("60-minutes-or-less", "vegetarian"),
            nutrition = Nutrition(
                calories = 150.0,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 20.0,
                saturatedFat = 0.0,
                carbohydrates = 4.0
            ),
            stepNumbers = 11,
            steps = listOf(
                "make a choice and proceed", "cut into half or fourths", "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        ), MealItem(
            id = 1377399,
            name = "3",
            preparationTimeInMinutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            mealTags = listOf("60-minutes-or-less", "vegetarian"),
            nutrition = Nutrition(
                calories = 100.0,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 10.0,
                saturatedFat = 0.0,
                carbohydrates = 4.0
            ),
            stepNumbers = 11,
            steps = listOf(
                "make a choice and proceed", "cut into half or fourths", "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
    )

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        getMealsForGymUseCase = GetGymMealsUseCase(dataSource)
    }

    @Test
    fun `should return InvalidGetGymMealsException when calories is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()
        val calories = -1.0
        val protein = 10.0

        //When && Then
        shouldThrow<InvalidGetGymMealsException> {
            getMealsForGymUseCase.getMealsForGym(calories, protein)
        }
    }

    @Test
    fun `should return InvalidGetGymMealsException when protein is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()
        val calories = 20.0
        val protein = -10.0

        //When && Then
        shouldThrow<InvalidGetGymMealsException> {
            getMealsForGymUseCase.getMealsForGym(calories, protein)
        }
    }

    @Test
    fun `should return InvalidGetGymMealsException when number of meals is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()
        val calories = -1.0
        val protein = 10.0
        val numberOfMeals = -10

        //When && Then
        shouldThrow<InvalidGetGymMealsException> {
            getMealsForGymUseCase.getMealsForGym(calories, protein, numberOfMeals = numberOfMeals)
        }
    }

    @Test
    fun `should return InvalidGetGymMealsException when calories tolerance is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()
        val calories = -1.0
        val protein = 10.0
        val caloriesTolerance = -20

        //When && Then
        shouldThrow<InvalidGetGymMealsException> {
            getMealsForGymUseCase.getMealsForGym(calories, protein, caloriesTolerance = caloriesTolerance)
        }
    }

    @Test
    fun `should return InvalidGetGymMealsException when protein tolerance is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()
        val calories = -1.0
        val protein = 10.0
        val proteinTolerance = -20

        //When && Then
        shouldThrow<InvalidGetGymMealsException> {
            getMealsForGymUseCase.getMealsForGym(calories, protein, proteinTolerance = proteinTolerance)
        }
    }

    @Test
    fun `should return the meals when give it existing values`() {
        // Given

        every { dataSource.getAllMeals() } returns getMealItem()

        //When
        val result = getMealsForGymUseCase.getMealsForGym(calories = 200.0, protein = 20.0)

        //Then
        result.shouldContain(getMealItem().first())
    }


    @Test
    fun `should return empty list when given random values`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()

        // When
        val result = getMealsForGymUseCase.getMealsForGym(calories = 999.0, protein = 999.0)

        // Then
        result.shouldBeEmpty()
    }

    @Test
    fun `should not return meal when only one criterion matches`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealItem()

        // When
        val result = getMealsForGymUseCase.getMealsForGym(calories = 200.0, protein = 1000.0)

        // Then
        result.shouldBeEmpty()
    }

}