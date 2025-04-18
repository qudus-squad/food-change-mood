package logic

import kotlinx.datetime.LocalDate
import model.IngredientGameRound
import model.MealItem
import model.Nutrition

class IngredientGameUseCase {

    // TODO Need Implement from Meal Repo
    private val meals: List<MealItem> = listOf(
        MealItem(
            id = 137739,
            name = "arriba baked winter squash mexican style",
            minutes = 55,
            contributorId = 47892,
            submitted = LocalDate.parse("2005-09-16"),
            tags = listOf("60-minutes-or-less", "vegetarian", "mexican"),
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
                "make a choice and proceed",
                "cut into half or fourths",
                "remove seeds"
            ),
            description = "Spicy or sweet squash recipe from Mexico.",
            ingredients = listOf("winter squash", "mexican seasoning", "olive oil"),
            ingredientNumbers = 3
        ),
        MealItem(
            id = 31490,
            name = "shrimp and crab salad",
            minutes = 30,
            contributorId = 26278,
            submitted = LocalDate.parse("2002-06-17"),
            tags = listOf("30-minutes-or-less", "seafood"),
            nutrition = Nutrition(
                calories = 173.4,
                totalFat = 18.0,
                sugar = 0.0,
                sodium = 17.0,
                protein = 22.0,
                saturatedFat = 35.0,
                carbohydrates = 1.0
            ),
            stepNumbers = 5,
            steps = listOf(
                "mix ingredients",
                "chill in fridge",
                "serve cold"
            ),
            description = "A refreshing seafood salad with shrimp and crab.",
            ingredients = listOf("shrimp", "crab", "mayonnaise", "lemon"),
            ingredientNumbers = 4
        )
    )

    private var points: Int = 0
    private var correctAnswers: Int = 0
    private val playedMeals = mutableSetOf<Int>()

    fun startNewRound(): IngredientGameRound {

        // Filter meals that contain ingredients and are not duplicated
        val availableMeals = meals
            .filter { it.ingredients.isNotEmpty() }
            .filter { it.id !in playedMeals }

        if (availableMeals.isEmpty()) {
            throw InsufficientMealsException("No more meals available to play the game")
        }

        // Choose a random meal
        val selectedMeal = availableMeals.random()
        playedMeals.add(selectedMeal.id)

        // Choose a random correct ingredient from the meal
        val correctIngredient = selectedMeal.ingredients.random()

        // Choose two incorrect ingredients from other meals
        val otherIngredients = meals
            .filter { it.id != selectedMeal.id }
            .flatMap { it.ingredients }
            .distinct()
            .filter { it != correctIngredient }

        if (otherIngredients.size < 2) {
            throw InsufficientMealsException("Not enough ingredients to create a game round")
        }

        // Randomly select two wrong ingredients
        val wrongIngredients = otherIngredients.shuffled().take(2)

        // Mix up the options
        val options = (listOf(correctIngredient) + wrongIngredients).shuffled()

        return IngredientGameRound(
            mealName = selectedMeal.name,
            options = options,
            correctIngredient = correctIngredient
        )

    }

    // Process user guesswork
    fun processGuess(userGuess: String, correctIngredient: String): GameResult {
        if (userGuess == correctIngredient) {

            // Correct answer
            points += POINTS_PER_CORRECT_ANSWER
            correctAnswers++


            if (correctAnswers >= MAX_CORRECT_ANSWERS) {
                // The game ends after 15 correct answers
                return GameResult.GameCompleted(points)
            }

            return GameResult.Correct(points, correctAnswers)
        } else {
            // Incorrect answer, the game ends
            return GameResult.Incorrect(points, correctAnswers)
        }
    }

    // Reset the game state to start a new game.
    fun resetGame() {
        points = 0
        correctAnswers = 0
        playedMeals.clear()
    }

    companion object {
        private const val POINTS_PER_CORRECT_ANSWER = 1000
        private const val MAX_CORRECT_ANSWERS = 15
    }

}

// A model to represent the result of a guess or the final score of a game.
sealed class GameResult {

    // The guess is correct
    data class Correct(val currentPoints: Int, val correctAnswers: Int) : GameResult()

    // The guess is wrong, the game is over.
    data class Incorrect(val currentPoints: Int, val correctAnswers: Int) : GameResult()

    // The game ends after 15 correct answers.
    data class GameCompleted(val currentPoints: Int) : GameResult()

}

class InsufficientMealsException(message: String) : Exception(message)

fun main() {
    val gameUseCase = IngredientGameUseCase()

    gameUseCase.resetGame()

    while (true) {
        try {
            // Start a new round
            val round = gameUseCase.startNewRound()
            println("Guess the ingredient for: ${round.mealName}")
            round.options.forEachIndexed { index, option ->
                println("${index + 1}. $option")
            }

            // Simulate the user simulation
            val guessIndex = readlnOrNull()?.toIntOrNull()?.let { it - 1 } ?: -1
            if (guessIndex !in 0..2) {
                println("Invalid choice")
                continue
            }
            val guess = round.options[guessIndex]

            // Process the guess
            when (val result = gameUseCase.processGuess(guess, round.correctIngredient)) {
                is GameResult.Correct -> {
                    println("Correct! Points: ${result.currentPoints}, Correct Answers: ${result.correctAnswers}")
                }

                is GameResult.Incorrect -> {
                    println("Incorrect! Game Over. Final Points: ${result.currentPoints}, Correct Answers: ${result.correctAnswers}")
                    break
                }

                is GameResult.GameCompleted -> {
                    println("Congratulations! Game Completed. Final Points: ${result.currentPoints}")
                    break
                }
            }
        } catch (e: InsufficientMealsException) {
            println("You've answered all available meals!")
            break
        }
    }
}