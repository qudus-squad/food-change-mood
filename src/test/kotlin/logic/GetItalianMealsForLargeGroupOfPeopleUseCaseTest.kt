package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetItalianMealsForLargeGroupOfPeopleUseCaseTest {
    private lateinit var datasource: FoodChangeModeDataSource
    private lateinit var getItalianMealsForLargeGroupOfPeopleUseCase: GetItalianMealsForLargeGroupOfPeopleUseCase

    @BeforeEach
    fun setup() {
        datasource = mockk(relaxed = true)
        getItalianMealsForLargeGroupOfPeopleUseCase = GetItalianMealsForLargeGroupOfPeopleUseCase(datasource)
    }

    private fun getMealsItems() = listOf(
        MealItem(
            id = 1,
            name = "baked squash italian style",
            preparationTimeInMinutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            mealTags = listOf(
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
            steps = listOf(
                "make a choice and proceed with recipe",
                "depending on size of squash, cut into half or fourths",
                "remove seeds"
            ),
            description = "Autumn is my favorite time of year to cook! This recipe can be prepared either spicy or sweet, your choice! Two of my posted italian-inspired seasoning mix recipes are offered as suggestions.",
            ingredients = listOf(
                "winter squash",
                "italian seasoning",
                "mixed spice",
                "honey",
                "butter",
                "olive oil",
                "salt"
            ),
            ingredientNumbers = 7
        ),
        MealItem(
            id = 2,
            name = "arriba baked winter squash mexican style",
            preparationTimeInMinutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            mealTags = listOf(
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
                "for-large-groups",
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
        ),
        MealItem(
            id = 3,
            name = "italian pasta for large group",
            preparationTimeInMinutes = 30,
            contributorId = 12345,
            submitted = LocalDate.parse("2023-03-01"),
            mealTags = listOf(
                "italian",
                "time-to-make",
                "course",
                "main-ingredient",
                "cuisine",
                "preparation",
                "occasion",
                "north-american",
                "side-dishes",
                "vegetables",
                "for-large-groups"
            ),
            nutrition = Nutrition(
                100.0,
                0.0,
                10.0,
                0.0,
                5.0,
                0.0,
                20.0
            ),
            stepNumbers = 5,
            steps = listOf(
                "boil water",
                "cook pasta",
                "add sauce"
            ),
            description = "A big dish of italian pasta, perfect for large groups.",
            ingredients = listOf(
                "pasta",
                "tomato sauce",
                "cheese"
            ),
            ingredientNumbers = 6
        )
    )

    @Test
    fun `should return only italian meals when search by italian meals`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()

        // When
        val result = getItalianMealsForLargeGroupOfPeopleUseCase
            .getItalianMealsForLargeGroupOfPeople("italian")

        // Then
        result.map { it.name }.shouldContainExactlyInAnyOrder(
            "baked squash italian style",
            "italian pasta for large group"
        )
    }

    @Test
    fun `should return only italian meals when search by unformatted valid country name`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()

        // When
        val result = getItalianMealsForLargeGroupOfPeopleUseCase
            .getItalianMealsForLargeGroupOfPeople("MeXicaN")

        // Then
        result.map { it.name }.shouldContainExactly(
            "arriba baked winter squash mexican style"
        )
    }

    @Test
    fun `should default to italian when no country name is provided`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()

        // When
        val result = getItalianMealsForLargeGroupOfPeopleUseCase.getItalianMealsForLargeGroupOfPeople()

        // Then
        result.map { it.name }.shouldContainExactlyInAnyOrder(
            "baked squash italian style",
            "italian pasta for large group"
        )
    }

    @Test
    fun `should throw NoMealsFoundException when there are no large group meals for searched country`() {
        // Given
        every { datasource.getAllMeals() } returns getMealsItems()

        // When & Then
        shouldThrow<NoMealsFoundException> {
            getItalianMealsForLargeGroupOfPeopleUseCase.getItalianMealsForLargeGroupOfPeople("American")
        }
    }
}