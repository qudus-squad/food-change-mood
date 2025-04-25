package logic
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.NoSweetsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.Strings.EGGS_KEYWORDS
import utils.Strings.SWEETS_KEYWORDS
import kotlin.test.assertTrue

class GetSweetsWithNoEggsUseCaseTest() {
    private lateinit var datasource: FoodChangeModeDataSource
    private lateinit var getSweetsWithNoEggsUseCase: GetSweetsWithNoEggsUseCase

    @BeforeEach
    fun setup() {
        datasource = mockk(relaxed = true)
        getSweetsWithNoEggsUseCase = GetSweetsWithNoEggsUseCase(datasource)
    }

    private fun getMealsItem() = listOf(
        MealItem(
            id = 1,
            name = "sweet With No Eggs",
            preparationTimeInMinutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            mealTags = listOf(
                "sweet"
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
            steps = listOf(
                "make a choice and proceed with recipe",
                "depending on size of squash, cut into half or fourths",
                "remove seeds"
            ),
            description = "Autumn is my favorite time of year to cook! This recipe can be prepared either spicy or , your choice! Two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
            ingredients = listOf(
                ""
            ),
            ingredientNumbers = 7
        ), MealItem(
            id = 1,
            name = "Sweet With Eggs",
            preparationTimeInMinutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            mealTags = listOf(
                "sweet"
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
            steps = listOf(
            ),
            description = "Autumn is my favorite time of year to cook! This recipe can be prepared either spicy or , your choice! Two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
            ingredients = listOf(
                "eggs"
            ),
            ingredientNumbers = 7
        )
    )


    @Test
    fun `should return only sweets with no eggs in ingredients and when found in the meals list`() {
        //Given
        every { datasource.getAllMeals() } returns getMealsItem()

        //When
        val result = getSweetsWithNoEggsUseCase.getAllSweets()

        //Then
        result.forEach { sweet ->
            assertTrue(sweet.mealTags.any { it.contains(SWEETS_KEYWORDS, ignoreCase = true) } &&
                    sweet.ingredients.none { it.contains(EGGS_KEYWORDS, ignoreCase = true) })
        }
    }

    @Test
    fun `should exclude sweets with eggs in ingredients when found in meals list`() {
        //Given
        every { datasource.getAllMeals() } returns getMealsItem()
        //When
        val result = getSweetsWithNoEggsUseCase.getAllSweets()
        //Then
        result.forEach { sweet ->
            assertTrue(sweet.mealTags.any { it.contains(SWEETS_KEYWORDS, ignoreCase = true) } &&
                    sweet.ingredients.none { it.contains(EGGS_KEYWORDS, ignoreCase = true) })
        }
    }

    @Test
    fun `should throw NoSweetsFoundException when there is no egg-free sweet in meals list`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItem().map {
            it.copy(mealTags = it.mealTags - "sweet")
        }
        // When & Then
        shouldThrow<NoSweetsFoundException> {
            getSweetsWithNoEggsUseCase.getAllSweets()
        }
    }

    @Test
    fun `should return available sweets when number is fewer than requested`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItem()

        //When
        val result = getSweetsWithNoEggsUseCase.suggestSweetsWithNoEgg()

        //Then
        result.map { it.name }.shouldContainExactly("sweet With No Eggs")
    }

    @Test
    fun `should throw NoSweetsFoundException when there is no egg-free sweet to suggest`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItem().map {
            it.copy(mealTags = it.mealTags - "sweet")
        }
        // When & Then
        shouldThrow<NoSweetsFoundException> {
            getSweetsWithNoEggsUseCase.suggestSweetsWithNoEgg()
        }
    }
}