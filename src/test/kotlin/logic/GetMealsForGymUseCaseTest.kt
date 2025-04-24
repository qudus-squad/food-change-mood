package logic

import io.kotest.matchers.collections.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMealsForGymUseCaseTest {

    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var getMealsForGymUseCase: GetMealsForGymUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        getMealsForGymUseCase = GetMealsForGymUseCase(dataSource)
    }

    @Test
    fun `should return the meals when give it existing values`() {
        // Given
        val meal1 = MealItem(
            id = 13773,
            name = "1",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        val meal2 = MealItem(
            id = 137739,
            name = "2",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        val meal3 = MealItem(
            id = 1377399,
            name = "3",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        every { dataSource.getAllMeals() } returns listOf(meal1, meal2, meal3)

        //When
        val result = getMealsForGymUseCase.getMealsForGym(calories = 200.0, protein = 20.0)

        //Then
        result.shouldContain(meal1)
    }

    @Test
    fun `should return the empty list when give it a negative value`() {
        // Given
        val meal1 = MealItem(
            id = 13773,
            name = "1",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        val meal2 = MealItem(
            id = 137739,
            name = "2",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        val meal3 = MealItem(
            id = 1377399,
            name = "3",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        every { dataSource.getAllMeals() } returns listOf(meal1, meal2, meal3)

        //When
        val result = getMealsForGymUseCase.getMealsForGym(calories = -200.0, protein = -20.0)

        //Then
        result.shouldBeEmpty()

    }

    @Test
    fun `should return empty list when given random values`() {
        // Given
        val meal1 = MealItem(
            id = 13773,
            name = "1",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        val meal2 = MealItem(
            id = 137739,
            name = "2",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        val meal3 = MealItem(
            id = 1377399,
            name = "3",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe.",
            ingredients = listOf("winter squash", "mexican seasoning"),
            ingredientNumbers = 2
        )
        every { dataSource.getAllMeals() } returns listOf(meal1, meal2, meal3)

        // When
        val result = getMealsForGymUseCase.getMealsForGym(calories = 999.0, protein = 999.0)

        // Then
        result.shouldBeEmpty()
    }

    @Test
    fun `should not return meal when only one criterion matches`() {
        // Given
        val meal1 = MealItem(
            id = 13773,
            name = "1",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
            nutrition = Nutrition(
                calories = 0.0,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 0.0,
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
        )
        val meal2 = MealItem(
            id = 137739,
            name = "2",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
            nutrition = Nutrition(
                calories = 200.0,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 0.0,
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
        )
        val meal3 = MealItem(
            id = 1377399,
            name = "3",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("60-minutes-or-less", "vegetarian"),
            nutrition = Nutrition(
                calories = 0.0,
                totalFat = 0.0,
                sugar = 13.0,
                sodium = 0.0,
                protein = 20.0,
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
        )
        every { dataSource.getAllMeals() } returns listOf(meal1, meal2, meal3)

        // When
        val result = getMealsForGymUseCase.getMealsForGym(calories = 200.0, protein = 20.0)

        // Then
        result.shouldBeEmpty()
    }

}