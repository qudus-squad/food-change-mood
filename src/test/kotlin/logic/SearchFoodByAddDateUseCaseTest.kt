package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchFoodByAddDateUseCaseTest {

    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var searchMealsByAddDateUseCase: SearchMealsByAddDateUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        searchMealsByAddDateUseCase = SearchMealsByAddDateUseCase(dataSource)
    }

    private fun getTestMeals() = listOf(
        MealItem(
            id = 1,
            name = "classic mashed potatoes",
            minutes = 20,
            contributorId = 12345,
            submitted = LocalDate.parse("2005-06-12"),
            tags = listOf("side-dish", "vegetarian", "easy"),
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
        ),
        MealItem(
            id = 2,
            name = "garlic mashed potatoes",
            minutes = 25,
            contributorId = 12346,
            submitted = LocalDate.parse("2005-07-15"),
            tags = listOf("side-dish", "vegetarian", "garlic"),
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
        ),
        MealItem(
            id = 3,
            name = "sweet potato mash",
            minutes = 30,
            contributorId = 12347,
            submitted = LocalDate.parse("2005-08-20"),
            tags = listOf("side-dish", "vegetarian", "sweet"),
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
    fun `should return food matches when searched with the added date`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val addDate = "2005-06-12"

        // When
        val result = searchMealsByAddDateUseCase.getSearchMealsByAddDate(addDate)

        // Then
        result.shouldContainExactly(getTestMeals().first())
    }

    @Test
    fun `should return throw NoMealsFoundException when no matches found`() {
        // given
        every { dataSource.getAllMeals() } returns getTestMeals()
        val addDate = "2005-06-01"

        // When & Then
        shouldThrow<NoMealsFoundException> {
            searchMealsByAddDateUseCase.getSearchMealsByAddDate(addDate)
        }
    }

}