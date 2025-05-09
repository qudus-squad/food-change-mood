package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import logic.GetMealsSuggestionUseCase.GetMealsSuggestionInputs
import logic.validation_use_cases.ValidInputForGetMealsSuggestion
import model.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class GetMealsSuggestionUseCaseTest {

    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var validInputForGetMealsSuggestion: ValidInputForGetMealsSuggestion
    private lateinit var getMealsSuggestionUseCase: GetMealsSuggestionUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        validInputForGetMealsSuggestion = ValidInputForGetMealsSuggestion()
        getMealsSuggestionUseCase = GetMealsSuggestionUseCase(dataSource, validInputForGetMealsSuggestion)
    }


    private fun getMealsItem() = listOf(
        MealItem(
            id = 7,
            name = "quick veggie stir fry",
            preparationTimeInMinutes = 15,
            contributorId = 67890,
            submitted = LocalDate.parse("2021-05-15"),
            mealTags = listOf("quick", "vegetarian", "asian", "healthy", "30-minutes-or-less"),
            nutrition = Nutrition(
                calories = 280.0,
                totalFat = 12.0,
                sugar = 5.0,
                sodium = 350.0,
                protein = 8.0,
                saturatedFat = 2.0,
                carbohydrates = 35.0
            ),
            stepNumbers = 5,
            steps = listOf(
                "heat oil in wok", "add chopped vegetables", "stir fry for 5 minutes", "add sauce and toss", "serve hot"
            ),
            description = "A quick and healthy vegetable stir fry that's ready in 15 minutes.",
            ingredients = listOf(
                "mixed vegetables", "soy sauce", "sesame oil", "garlic", "ginger"
            ),
            ingredientNumbers = 5
        ), MealItem(
            id = 8,
            name = "classic beef burger",
            preparationTimeInMinutes = 25,
            contributorId = 54321,
            submitted = LocalDate.parse("2019-07-22"),
            mealTags = listOf("american", "beef", "main-dish", "comfort-food"),
            nutrition = Nutrition(
                calories = 550.0,
                totalFat = 30.0,
                sugar = 6.0,
                sodium = 480.0,
                protein = 25.0,
                saturatedFat = 10.0,
                carbohydrates = 40.0
            ),
            stepNumbers = 6,
            steps = listOf(
                "form beef patties",
                "season with salt and pepper",
                "grill for 4 minutes per side",
                "toast buns",
                "assemble with toppings",
                "serve with fries"
            ),
            description = "Juicy classic beef burger with all the fixings.",
            ingredients = listOf(
                "ground beef", "burger buns", "lettuce", "tomato", "onion", "cheese", "ketchup", "mustard"
            ),
            ingredientNumbers = 8
        ), MealItem(
            id = 9,
            name = "creamy mushroom risotto",
            preparationTimeInMinutes = 40,
            contributorId = 98765,
            submitted = LocalDate.parse("2020-11-30"),
            mealTags = listOf("italian", "vegetarian", "comfort-food", "rice-dish"),
            nutrition = Nutrition(
                calories = 420.0,
                totalFat = 15.0,
                sugar = 3.0,
                sodium = 320.0,
                protein = 10.0,
                saturatedFat = 7.0,
                carbohydrates = 60.0
            ),
            stepNumbers = 7,
            steps = listOf(
                "sauté mushrooms",
                "toast rice",
                "add warm broth gradually",
                "stir constantly",
                "add mushrooms back",
                "finish with butter and cheese",
                "rest before serving"
            ),
            description = "Creamy Italian risotto with earthy mushrooms.",
            ingredients = listOf(
                "arborio rice", "mushrooms", "vegetable broth", "white wine", "parmesan", "onion", "butter"
            ),
            ingredientNumbers = 7
        ), MealItem(
            id = 10,
            name = "simple green salad",
            preparationTimeInMinutes = 10,
            contributorId = 11223,
            submitted = LocalDate.parse("2022-02-14"),
            mealTags = listOf("salad", "healthy", "vegan", "quick", "no-cook"),
            nutrition = Nutrition(
                calories = 150.0,
                totalFat = 10.0,
                sugar = 2.0,
                sodium = 120.0,
                protein = 3.0,
                saturatedFat = 1.5,
                carbohydrates = 12.0
            ),
            stepNumbers = 3,
            steps = listOf(
                "wash and chop greens", "prepare dressing", "toss and serve"
            ),
            description = "Fresh green salad with light vinaigrette.",
            ingredients = listOf(
                "mixed greens", "cucumber", "tomato", "olive oil", "lemon juice", "salt"
            ),
            ingredientNumbers = 6
        ), MealItem(
            id = 11,
            name = "chocolate chip cookies",
            preparationTimeInMinutes = 30,
            contributorId = 44556,
            submitted = LocalDate.parse("2018-09-05"),
            mealTags = listOf("dessert", "baking", "cookies", "sweet"),
            nutrition = Nutrition(
                calories = 180.0,
                totalFat = 9.0,
                sugar = 15.0,
                sodium = 85.0,
                protein = 2.0,
                saturatedFat = 5.0,
                carbohydrates = 24.0
            ),
            stepNumbers = 7,
            steps = listOf(
                "cream butter and sugar",
                "add eggs and vanilla",
                "mix dry ingredients",
                "fold in chocolate chips",
                "scoop and bake",
                "cool before serving"
            ),
            description = "Classic chewy chocolate chip cookies.",
            ingredients = listOf(
                "flour", "butter", "sugar", "eggs", "chocolate chips", "vanilla", "baking soda"
            ),
            ingredientNumbers = 7
        )
    )


    @Test
    fun `should throw InvalidCountOfSuggestionsInput when number Of suggestions is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val input = GetMealsSuggestionInputs(
            countOfSuggestions = -20, preparationTime = 30, countOfIngredients = 5, countOfPreparationSteps = 6
        )
        // When & Then
        shouldThrow<InvalidCountOfSuggestionsInput> {
            getMealsSuggestionUseCase.suggestEasyMeals(
                input
            )
        }
    }

    @Test
    fun `should throw InvalidPreparationTimeInput when preparation time is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val input = GetMealsSuggestionInputs(
            countOfSuggestions = 10, preparationTime = -100, countOfIngredients = -5, countOfPreparationSteps = 6
        )
        // When & Then
        shouldThrow<InvalidPreparationTimeInput> {
            getMealsSuggestionUseCase.suggestEasyMeals(input)
        }
    }

    @Test
    fun `should throw InvalidCountOfIngredientsInput when number Of ingredients is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val input = GetMealsSuggestionInputs(
            countOfSuggestions = 10, preparationTime = 30, countOfIngredients = -6, countOfPreparationSteps = 6
        )

        // When & Then
        shouldThrow<InvalidCountOfIngredientsInput> {
            getMealsSuggestionUseCase.suggestEasyMeals(input)
        }
    }

    @Test
    fun `should throw InvalidCountOfPreparationStepsInput when number of preparationSteps is negative`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val input = GetMealsSuggestionInputs(
            countOfSuggestions = 10, preparationTime = 30, countOfIngredients = 5, countOfPreparationSteps = -3
        )

        // When & Then
        shouldThrow<InvalidCountOfPreparationStepsInput> {
            getMealsSuggestionUseCase.suggestEasyMeals(input)
        }
    }


    @ParameterizedTest
    @CsvFileSource(
        files = ["easy_meal_suggestions.csv"], numLinesToSkip = 1
    )
    fun `should return suggestions when inputs are valid`(
        countOfSuggestions: Int,
        preparationTime: Int,
        countOfIngredients: Int,
        countOfPreparationSteps: Int,
        expectedMealNames: String

    ) {
        every { dataSource.getAllMeals() } returns getMealsItem()
        val dataSourceCsv = GetMealsSuggestionUseCase(dataSource, validInputForGetMealsSuggestion)

        // When

        val result = dataSourceCsv.suggestEasyMeals(
            GetMealsSuggestionInputs(
                countOfSuggestions, preparationTime, countOfIngredients, countOfPreparationSteps
            )
        )

        // Then
        val expectedNames = expectedMealNames.split("|")
        result.map { it.name }.shouldContainExactlyInAnyOrder(expectedNames)
    }

    @Test
    fun `should throw empty list when all inputs not fitted for meals suggestion`() {
        // Given
        every { dataSource.getAllMeals() } returns getMealsItem()
        val input = GetMealsSuggestionInputs(
            countOfSuggestions = 1, preparationTime = 1, countOfIngredients = 1, countOfPreparationSteps = 1
        )
        // When
        val result = getMealsSuggestionUseCase.suggestEasyMeals(
            input
        )
        // Then
        result.shouldBeEmpty()
    }
}