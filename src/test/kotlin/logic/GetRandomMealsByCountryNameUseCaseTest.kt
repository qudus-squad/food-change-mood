package logic


import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.InvalidCountryNameException
import model.MealItem
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetRandomMealsByCountryNameUseCaseTest {
    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var getRandomMealsByCountryNameUseCase: GetRandomMealsByCountryNameUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        getRandomMealsByCountryNameUseCase = GetRandomMealsByCountryNameUseCase(dataSource)
    }

    private fun getMealsItem() = listOf(
        MealItem(
            id = 1,
            name = "arriba baked winter squash mexican style",
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
                "mexican"
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
            description = "Autumn is my favorite time of year to cook! This recipe can be prepared either spicy or sweet, your choice! Two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
            ingredients = listOf(
                "winter squash",
                "mexican seasoning",
                "mixed spice",
                "honey",
                "butter",
                "olive oil",
                "salt"
            ),
            ingredientNumbers = 7
        )
    )

    @Test
    fun `should return meals in searched country when search with a valid formatted country name`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = "mexican"
        val randomMealsNumber = 1

        // When
        val result = getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)

        // Then
        result.map { it.name }.shouldContainExactly("arriba baked winter squash mexican style")
    }

    @Test
    fun `should return meals in searched country when search with a valid unformatted country name`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = "MeXicaN"
        val randomMealsNumber = 1

        // When
        val result = getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)

        // Then
        result.map { it.name }.shouldContainExactly("arriba baked winter squash mexican style")
    }

    @Test
    fun `should return meals in searched country when search with a valid unformatted country name (has white spaces)`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = "  mexican  "
        val randomMealsNumber = 1

        // When
        val result = getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)

        // Then
        result.map { it.name }.shouldContainExactly("arriba baked winter squash mexican style")
    }

    @Test
    fun `should throw InvalidCountryException when search with empty country name`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = ""
        val randomMealsNumber = 1

        // When & Then
        assertThrows<InvalidCountryNameException> {
            getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)
        }
    }

    @Test
    fun `should throw InvalidCountryException when search with invalid country name`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = "Mexican!@#"
        val randomMealsNumber = 1

        // When & Then
        assertThrows<InvalidCountryNameException> {
            getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)
        }
    }

    @Test
    fun `should throw InvalidCountryException when search with blank country name`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = "   "  // Whitespace only
        val randomMealsNumber = 1

        // When & Then
        assertThrows<InvalidCountryNameException> {
            getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)
        }
    }

    @Test
    fun `should throw NoMealsFoundException when no meals found`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val countryName = "italian"
        val randomMealsNumber = 1

        // When & Then
        assertThrows<NoMealsFoundException> {
            getRandomMealsByCountryNameUseCase.getRandomMealsByCountry(countryName, randomMealsNumber)
        }
    }
}