package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.GameResult
import model.MealItem
import model.NotEnoughMealsException
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StartIngredientGameUseCaseTest {

    private lateinit var dataSource: FoodChangeModeDataSource
    private lateinit var startIngredientGameUseCase: StartIngredientGameUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk(relaxed = true)
        startIngredientGameUseCase = StartIngredientGameUseCase(dataSource)
    }

    private fun getMeals() = listOf(
        MealItem(
            id = 31490,
            name = "a bit different breakfast pizza",
            ingredients = listOf("prepared pizza crust", "sausage patty", "eggs", "milk", "salt and pepper", "cheese"),
            submitted = LocalDate.parse("2002-06-17"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 112140,
            name = "all in the kitchen chili",
            ingredients = listOf(
                "ground beef",
                "yellow onions",
                "diced tomatoes",
                "kidney beans",
                "chili powder",
                "salt",
                "cheddar cheese"
            ),
            submitted = LocalDate.parse("2005-02-25"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 59389,
            name = "alouette potatoes",
            ingredients = listOf(
                "new potatoes",
                "shallots",
                "parsley",
                "tarragon",
                "olive oil",
                "red wine vinegar",
                "salt",
                "pepper"
            ),
            submitted = LocalDate.parse("2003-04-14"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 44061,
            name = "amish tomato ketchup for canning",
            ingredients = listOf("tomato juice", "apple cider vinegar", "sugar", "salt", "clove oil", "cinnamon oil"),
            submitted = LocalDate.parse("2002-10-25"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 5289,
            name = "apple a day milk shake",
            ingredients = listOf("milk", "vanilla ice cream", "frozen apple juice concentrate", "apple"),
            submitted = LocalDate.parse("1999-12-06"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 25274,
            name = "aww marinated olives",
            ingredients = listOf(
                "fennel seeds",
                "green olives",
                "ripe olives",
                "garlic",
                "peppercorn",
                "orange rind",
                "orange juice",
                "red chile",
                "extra virgin olive oil"
            ),
            submitted = LocalDate.parse("2002-04-14"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 67888,
            name = "backyard style barbecued ribs",
            ingredients = listOf(
                "pork spareribs",
                "soy sauce",
                "fresh garlic",
                "fresh ginger",
                "chili powder",
                "fresh coarse ground black pepper",
                "salt",
                "fresh cilantro leaves",
                "tomato sauce",
                "brown sugar",
                "yellow onion",
                "white vinegar",
                "honey"
            ),
            submitted = LocalDate.parse("2003-07-30"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 70971,
            name = "bananas 4 ice cream pie",
            ingredients = listOf(
                "chocolate sandwich style cookies",
                "chocolate syrup",
                "vanilla ice cream",
                "bananas",
                "strawberry ice cream",
                "whipped cream"
            ),
            submitted = LocalDate.parse("2003-09-10"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 75452,
            name = "beat this banana bread",
            ingredients = listOf(
                "sugar",
                "unsalted butter",
                "bananas",
                "eggs",
                "fresh lemon juice",
                "orange rind",
                "cake flour",
                "baking soda",
                "salt"
            ),
            submitted = LocalDate.parse("2003-11-04"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 109439,
            name = "berry good sandwich spread",
            ingredients = listOf("whole berry cranberry sauce", "sour cream", "prepared horseradish"),
            submitted = LocalDate.parse("2005-01-25"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 70971,
            name = "bananas 4 ice cream pie",
            ingredients = listOf(
                "chocolate sandwich style cookies",
                "chocolate syrup",
                "vanilla ice cream",
                "bananas",
                "strawberry ice cream",
                "whipped cream"
            ),
            submitted = LocalDate.parse("2003-09-10"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 75452,
            name = "beat this banana bread",
            ingredients = listOf(
                "sugar",
                "unsalted butter",
                "bananas",
                "eggs",
                "fresh lemon juice",
                "orange rind",
                "cake flour",
                "baking soda",
                "salt"
            ),
            submitted = LocalDate.parse("2003-11-04"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 109439,
            name = "berry good sandwich spread",
            ingredients = listOf("whole berry cranberry sauce", "sour cream", "prepared horseradish"),
            submitted = LocalDate.parse("2005-01-25"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 42198,
            name = "better than sex strawberries",
            ingredients = listOf(
                "vanilla wafers",
                "butter",
                "powdered sugar",
                "eggs",
                "whipping cream",
                "strawberry",
                "walnuts"
            ),
            submitted = LocalDate.parse("2002-10-03"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 67547,
            name = "better then bush's baked beans",
            ingredients = listOf(
                "great northern bean",
                "chicken bouillon cubes",
                "dark brown sugar",
                "molasses",
                "cornstarch",
                "onion",
                "garlic powder",
                "mustard powder",
                "chili powder",
                "salt",
                "black pepper",
                "bacon",
                "water"
            ),
            submitted = LocalDate.parse("2003-07-26"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 107517,
            name = "boat house collard greens",
            ingredients = listOf(
                "collard greens",
                "brown sugar",
                "molasses",
                "hot sauce",
                "whiskey",
                "ham hock",
                "salt"
            ),
            submitted = LocalDate.parse("2005-01-03"),
            ingredientNumbers = 0,
            preparationTimeInMinutes = 0,
            contributorId = 0,
            mealTags = emptyList(),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 88888,
            name = "spicy grilled chicken",
            ingredients = listOf("chicken breast", "paprika", "cayenne pepper", "salt", "olive oil"),
            ingredientNumbers = 5,
            preparationTimeInMinutes = 45,
            contributorId = 10500,
            submitted = LocalDate.parse("2004-06-20"),
            mealTags = listOf("main-dish", "grilled", "spicy"),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 99999,
            name = "classic beef stew",
            ingredients = listOf("beef chunks", "carrots", "potatoes", "onion", "beef broth"),
            ingredientNumbers = 5,
            preparationTimeInMinutes = 120,
            contributorId = 10600,
            submitted = LocalDate.parse("2003-11-15"),
            mealTags = listOf("stew", "main-dish", "comfort-food"),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 77777,
            name = "vegan lentil curry",
            ingredients = listOf("lentils", "coconut milk", "curry powder", "garlic", "spinach"),
            ingredientNumbers = 5,
            preparationTimeInMinutes = 40,
            contributorId = 10700,
            submitted = LocalDate.parse("2005-01-10"),
            mealTags = listOf("vegan", "curry", "main-dish"),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 66666,
            name = "shrimp pasta",
            ingredients = listOf("shrimp", "pasta", "garlic", "olive oil", "lemon juice"),
            ingredientNumbers = 5,
            preparationTimeInMinutes = 30,
            contributorId = 10800,
            submitted = LocalDate.parse("2004-03-18"),
            mealTags = listOf("pasta", "seafood", "main-dish"),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        ),
        MealItem(
            id = 55555,
            name = "classic pancakes",
            ingredients = listOf("flour", "milk", "eggs", "baking powder", "sugar"),
            ingredientNumbers = 5,
            preparationTimeInMinutes = 20,
            contributorId = 10900,
            submitted = LocalDate.parse("2002-08-30"),
            mealTags = listOf("breakfast", "pancakes", "easy"),
            nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            stepNumbers = 0,
            steps = emptyList(),
            description = ""
        )
    )

    @Test
    fun `should return true when the user enters the correct guess number`() {

        // Given
        every { dataSource.getAllMeals() } returns getMeals()

        // When
        val resultOfGame = startIngredientGameUseCase.startNewRound()

        // Then
        resultOfGame.optionsOfIngredients shouldContain resultOfGame.correctIngredient
    }

    @Test
    fun `should return incorrect result when user enters wrong guess number`() {
        // Given
        every { dataSource.getAllMeals() } returns getMeals()

        // When
        val gameRound = startIngredientGameUseCase.startNewRound()

        val wrongGuess =
            gameRound.optionsOfIngredients.first { ingredient -> ingredient != gameRound.correctIngredient }

        val processWrongGuess = startIngredientGameUseCase.processGuess(wrongGuess, gameRound.correctIngredient)

        // Then
        (processWrongGuess is GameResult.Incorrect) shouldBe true
    }

    @Test
    fun `should throw NotEnoughMealsException when no available meals`() {
        // Given
        every { dataSource.getAllMeals() } returns emptyList()

        // When & Then
        shouldThrow<NotEnoughMealsException> {
            startIngredientGameUseCase.startNewRound()
        }
    }

    @Test
    fun `should throw NotEnoughMealsException when no enough wrong ingredients`() {
        // Given
        val mealsWithLimitedIngredients = listOf(
            MealItem(
                id = 1,
                name = "test meal",
                ingredients = listOf("ingredient1"),
                ingredientNumbers = 1,
                preparationTimeInMinutes = 0,
                contributorId = 0,
                submitted = LocalDate.parse("2005-09-16"),
                mealTags = emptyList(),
                nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                stepNumbers = 0,
                steps = emptyList(),
                description = ""
            )
        )
        every { dataSource.getAllMeals() } returns mealsWithLimitedIngredients

        // When & Then
        shouldThrow<NotEnoughMealsException> {
            startIngredientGameUseCase.startNewRound()
        }
    }

    @Test
    fun `should stop the game and display the result if the user guesses 15 correct answers`() {
        // Given
        every { dataSource.getAllMeals() } returns getMeals()
        startIngredientGameUseCase.resetGame()

        var gameResult: GameResult? = null

        // When
        repeat(15) {
            val gameRound = startIngredientGameUseCase.startNewRound()
            gameResult =
                startIngredientGameUseCase.processGuess(gameRound.correctIngredient, gameRound.correctIngredient)
        }

        // Then
        (gameResult is GameResult.GameCompleted) shouldBe true
    }

    @Test
    fun `should add 1000 points to the score if the user guesses the correct answer`() {
        // Given
        every { dataSource.getAllMeals() } returns getMeals()
        startIngredientGameUseCase.resetGame()

        val gameRound = startIngredientGameUseCase.startNewRound()

        // When
        val gameResult =
            startIngredientGameUseCase.processGuess(gameRound.correctIngredient, gameRound.correctIngredient)

        // Then
        (gameResult is GameResult.Correct) shouldBe true
        (gameResult as GameResult.Correct).currentPoints shouldBe 1000
    }

    @Test
    fun `should exit the game and display the result if the user guesses one answer incorrectly`() {
        // Given
        every { dataSource.getAllMeals() } returns getMeals()
        startIngredientGameUseCase.resetGame()

        val gameRound = startIngredientGameUseCase.startNewRound()
        val wrongGuess = gameRound.optionsOfIngredients.first { it != gameRound.correctIngredient }

        // When
        val gameResult = startIngredientGameUseCase.processGuess(wrongGuess, gameRound.correctIngredient)

        // Then
        (gameResult is GameResult.Incorrect) shouldBe true
        (gameResult as GameResult.Incorrect).currentPoints shouldBe 0
    }
}
