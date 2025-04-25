package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.InvalidCountryNameException
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetIraqMealsUseCaseTest {
    private lateinit var datasource: FoodChangeModeDataSource
    private lateinit var getIraqMealsUseCase: GetIraqMealsUseCase

    @BeforeEach
    fun setup() {
        datasource = mockk(relaxed = true)
        getIraqMealsUseCase = GetIraqMealsUseCase(datasource)
    }

    private fun getMealsItems() = listOf(
        MealItem(
            id = 1,
            name = "baked squash italian style",
            minutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
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
                "italian",
                "for-large-groups"
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
            description = "A delicious Italian side dish, no mention of Iraq.",
            steps = emptyList(),
            ingredientNumbers = 6,
            ingredients = emptyList(),
        ), MealItem(
            id = 2,
            name = "iraqi lamb stew",
            minutes = 120,
            contributorId = 50231,
            submitted = LocalDate.parse("2012-03-21"),
            tags = listOf(
                "lamb", "stew", "iraqi", "middle-eastern", "dinner-party", "meat", "slow-cooker"
            ),
            nutrition = Nutrition(
                calories = 325.0,
                totalFat = 18.5,
                sugar = 2.0,
                sodium = 480.0,
                protein = 25.0,
                saturatedFat = 7.0,
                carbohydrates = 12.0
            ),
            stepNumbers = 7,
            description = "Traditional lamb stew from Iraq with deep spices.",
            steps = emptyList(),
            ingredientNumbers = 6,
            ingredients = emptyList(),
        ), MealItem(
            id = 3,
            name = "falafel wrap",
            minutes = 30,
            contributorId = 63481,
            submitted = LocalDate.parse("2010-07-05"),
            tags = listOf("vegetarian", "middle-eastern", "quick-meal", "street-food"),
            nutrition = Nutrition(
                calories = 280.0,
                totalFat = 10.0,
                sugar = 4.0,
                sodium = 650.0,
                protein = 10.0,
                saturatedFat = 2.0,
                carbohydrates = 30.0
            ),
            stepNumbers = 5,
            description = "Popular wrap often enjoyed in Syria, Lebanon",
            steps = emptyList(),
            ingredientNumbers = 6,
            ingredients = emptyList(),
        )
    )

    @Test
    fun `should return iraq meals when didnt enter country`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()

        // When
        val result = getIraqMealsUseCase.getIraqMeals()

        // Then
        result.map { it.name }.shouldContain("iraqi lamb stew")
    }

    @Test
    fun `should return NoMealsFoundException meals when didnt enter country and the data not contain iraq meals`() {
        // Given
        every { datasource.getAllMeals() } returns listOf(getMealsItems()[2])

        // When
        val result = getIraqMealsUseCase.getIraqMeals()
        // Then
        result.shouldBeEmpty()
    }


    @Test
    fun `should return meals when description contains country name`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()
        val countryName = "Lebanon"

        // When
        val result = getIraqMealsUseCase.getIraqMeals(countryName)

        // Then
        result.map { it.name }.shouldContain("falafel wrap")
    }

    @Test
    fun `should return NoMealsFoundException when description notContains country name`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()
        val countryName = "fsdgvlsdf"

        // When
        val result = getIraqMealsUseCase.getIraqMeals(countryName)
        // Then
        result.shouldBeEmpty()
    }

    @Test
    fun `should return InvalidCountryNameException when country name is empty`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()
        val countryName = ""

        // when && Then
        shouldThrow<InvalidCountryNameException> {
            getIraqMealsUseCase.getIraqMeals(countryName)
        }
    }
}