package logic

import di.appModule
import model.IngredientGameRound
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class IngredientGameUseCase(dataSource: FoodChangeModeDataSource) {


    private val meals = dataSource.getAllMeals()

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
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val gameUseCase = IngredientGameUseCase(dataSource)

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