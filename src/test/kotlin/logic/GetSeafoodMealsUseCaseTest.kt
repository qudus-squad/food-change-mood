package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContain
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.NoSeafoodMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.Strings.SEAFOOD_KEYWORDS

class GetSeafoodMealsUseCaseTest() {
    private lateinit var datasource: FoodChangeModeDataSource
    private lateinit var getSeafoodMealsUseCase: GetSeafoodMealsUseCase

    @BeforeEach
    fun setup() {
        datasource = mockk(relaxed = true)
        getSeafoodMealsUseCase = GetSeafoodMealsUseCase(datasource)
    }
    private fun getMealsItem() = listOf(
        MealItem(
            id = 1,
            name = "sea food meal",
            preparationTimeInMinutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            mealTags = listOf(
                ""
            ),
            nutrition = Nutrition(
                calories = 458.1,
                totalFat = 24.0,
                sugar = 6.0,
                sodium = 4.0,
                protein = 50.0,
                saturatedFat = 12.0,
                carbohydrates = 16.0
            ),
            stepNumbers = 11,
            steps = listOf(
                "make a choice and proceed with recipe",
                "depending on size of squash, cut into half or fourths",
                "remove seeds"
            ),
            description = "",
            ingredients = listOf("fish",),
            ingredientNumbers = 7
        ), MealItem(
            id = 3,
            name = "ordinary meal",
            preparationTimeInMinutes = 15,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            mealTags = listOf(
                ""
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
                ""
            ),
            ingredientNumbers = 7
        )
    )

    @Test
    fun `should return sea food meals when one of the keywords found in ingredients`() {

        //Given
        every { datasource.getAllMeals() } returns getMealsItem()

        //When
        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        //Then
        result.map { it.name }.shouldContain("sea food meal")
    }

    @Test
    fun `should throw NoSeafoodMealsFoundException when there are no meals found in list`() {

        // Given
        every { datasource.getAllMeals() } returns getMealsItem().map {
            it.copy(ingredients = it.ingredients - SEAFOOD_KEYWORDS)
        }
        // When & Then
        shouldThrow<NoSeafoodMealsFoundException> {
            getSeafoodMealsUseCase.getSeafoodMeals()
        }
    }
}