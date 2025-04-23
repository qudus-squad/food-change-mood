package logic
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.InvalidMealNumberException
import model.MealItem
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetRandomMealsWithPotatoUseCaseTest {
    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var getRandomMealsWithPotatoUseCase: GetRandomMealsWithPotatoUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        getRandomMealsWithPotatoUseCase = GetRandomMealsWithPotatoUseCase(dataSource)
    }
    private fun getMealsItem() = listOf(
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
        )

    )
    @Test
    fun `should return requested number of potato meals`() {
        // Given
        val mealsWithPotato = getMealsItem()
        every { dataSource.getAllMeals() } returns mealsWithPotato

        // When
        val result = getRandomMealsWithPotatoUseCase.getPotatoMeals()

        // Then
        result.forEach { meal ->
            meal.ingredients.any { it.contains("potato", ignoreCase = true) } shouldNotBe false
        }
    }
    @Test
    fun `should return all meals if available meals are fewer than requested`() {
        // Given
        val mealsWithPotato = listOf(getMealsItem().first(), getMealsItem().first())
        every { dataSource.getAllMeals() } returns mealsWithPotato

        // When
        val result = getRandomMealsWithPotatoUseCase.getPotatoMeals(5)

        // Then
        result.shouldHaveSize(mealsWithPotato.size)
    }

    @Test
    fun `should throw NoMealsFoundException when no meals contain potato`() {
        // Given
        val nonPotatoMeal = getMealsItem().first().copy(
            name = "grilled chicken",
            ingredients = listOf("chicken", "salt", "pepper")
        )
        every { dataSource.getAllMeals() } returns listOf(nonPotatoMeal)
        // When & Then
        assertThrows<NoMealsFoundException> {
            getRandomMealsWithPotatoUseCase.getPotatoMeals()
        }
    }

    @Test
    fun `should throw InvalidMealNumberException when meal number is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()

        // When & Then
        assertThrows<InvalidMealNumberException> {
            getRandomMealsWithPotatoUseCase.getPotatoMeals(-1)
        }
    }
}
