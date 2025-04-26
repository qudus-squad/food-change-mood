package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.InvalidNameMealException
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMealsByNameUseCaseTest {
    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var searchMealsByNameUseCase: SearchMealsByNameUseCase
    private lateinit var searchAlgorithm: SearchAlgorithm

    @BeforeEach
    fun setup() {
        dataSource = mockk()
        searchAlgorithm = SearchAlgorithm()
        searchMealsByNameUseCase = SearchMealsByNameUseCase(dataSource, searchAlgorithm)
    }

    private fun getTestMeals() = listOf(
        MealItem(
            id = 1,
            name = "classic mashed potatoes",
            preparationTimeInMinutes = 20,
            contributorId = 12345,
            submitted = LocalDate.parse("2005-06-12"),
            mealTags = listOf("side-dish", "vegetarian", "easy"),
            nutrition = Nutrition(
                calories = 110.0,
                totalFat = 4.0,
                sugar = 1.0,
                sodium = 250.0,
                protein = 2.0,
                saturatedFat = 2.5,
                carbohydrates = 20.0
            ),
            stepNumbers = 3,
            steps = listOf("boil potatoes", "mash with butter", "season with salt"),
            description = "Creamy and delicious mashed potatoes.",
            ingredients = listOf("potato", "butter", "salt"),
            ingredientNumbers = 3
        ), MealItem(
            id = 2,
            name = "garlic mashed potatoes",
            preparationTimeInMinutes = 25,
            contributorId = 12346,
            submitted = LocalDate.parse("2005-07-15"),
            mealTags = listOf("side-dish", "vegetarian", "garlic"),
            nutrition = Nutrition(
                calories = 120.0,
                totalFat = 5.0,
                sugar = 1.0,
                sodium = 300.0,
                protein = 2.5,
                saturatedFat = 3.0,
                carbohydrates = 22.0
            ),
            stepNumbers = 4,
            steps = listOf("boil potatoes", "mash with butter", "add garlic", "season with salt"),
            description = "Mashed potatoes with roasted garlic flavor.",
            ingredients = listOf("potato", "butter", "garlic", "salt"),
            ingredientNumbers = 4
        ), MealItem(
            id = 3,
            name = "sweet potato mash",
            preparationTimeInMinutes = 30,
            contributorId = 12347,
            submitted = LocalDate.parse("2005-08-20"),
            mealTags = listOf("side-dish", "vegetarian", "sweet"),
            nutrition = Nutrition(
                calories = 150.0,
                totalFat = 3.0,
                sugar = 8.0,
                sodium = 200.0,
                protein = 2.0,
                saturatedFat = 1.5,
                carbohydrates = 30.0
            ),
            stepNumbers = 3,
            steps = listOf("boil sweet potatoes", "mash with butter", "add cinnamon"),
            description = "Sweet version of mashed potatoes.",
            ingredients = listOf("sweet potato", "butter", "cinnamon"),
            ingredientNumbers = 3
        )
    )

    @Test
    fun `should return exact match when searched with full meal name`() {
        //given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = "classic mashed potatoes"

        // When
        val result = searchMealsByNameUseCase.searchMealsByName(mealName)

        // Then
        result.shouldContainExactly(1 to "classic mashed potatoes")
    }

    @Test
    fun `should return exact matches when searched with full meal name regardless of case`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = "CLASSIC MASHED POTATOES"

        // When
        val result = searchMealsByNameUseCase.searchMealsByName(mealName)

        // Then
        result.shouldContainExactly(1 to "classic mashed potatoes")
    }

    @Test
    fun `should return partial matches when searched with part of name`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = "mashed"

        // When
        val result = searchMealsByNameUseCase.searchMealsByName(mealName)

        // Then
        result.map { it.second } shouldBe listOf(
            "classic mashed potatoes", "garlic mashed potatoes"
        )
    }

    @Test
    fun `should return matches even with leading or trailing whitespace in search input`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = "   classic mashed potatoes   "

        // When
        val result = searchMealsByNameUseCase.searchMealsByName(mealName.trim())

        // Then
        result.shouldContainExactly(1 to "classic mashed potatoes")
    }

    @Test
    fun `should throw InvalidNameMealException when search input is empty`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = ""

        // When && then
        shouldThrow<InvalidNameMealException> {
            searchMealsByNameUseCase.searchMealsByName(mealName)
        }
    }

    @Test
    fun `should return empty list when no matches found`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = "nonexistent dish"

        // When
        val result = searchMealsByNameUseCase.searchMealsByName(mealName)

        // Then
        result shouldBe emptyList()
    }

    @Test
    fun `should not return matches when similarity is below threshold`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val mealName = "sweet potato fries"

        // When
        val result = searchMealsByNameUseCase.searchMealsByName(mealName)

        // Then
        result shouldBe emptyList()
    }
}