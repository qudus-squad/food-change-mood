package logic
import di.appModule
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class GuessGameUseCase(
    private val dataSource: FoodChangeModeDataSource
) {

    private val meals = dataSource.getAllMeals()
    private val maxAttempts = 3

    fun playGuessGame() {
        if (meals.isEmpty()) {
            println("No meals available to play the game.")
            return
        }
        if (!runGuessingRound()) {
            println("Game Over")
        }
    }

    private fun generateRandomMeal(): MealItem {
        return meals.random()
    }

    private fun runGuessingRound(): Boolean {
        val selectedMeal = generateRandomMeal()
        println("Guess the preparation time for ${selectedMeal.name} to get prepared (You have $maxAttempts attempts)")

        for (attemptsLeft in maxAttempts downTo 1) {
            val guess = getUserGuess() ?: continue

            if (isCorrectGuess(guess, selectedMeal)) {
                println("Congratulations! Your guess is correct.")
                return true
            } else {
                displayHint(guess, selectedMeal.minutes, attemptsLeft)
            }
        }

        println("You have run out of attempts. The correct preparation time was ${selectedMeal.minutes} minutes.")
        return false
    }

    private fun getUserGuess(): Int? {
        print("Enter your guess: ")
        return readlnOrNull()?.toIntOrNull()
    }

    private fun isCorrectGuess(guess: Int, selectedMeal: MealItem): Boolean {
        return guess == selectedMeal.minutes
    }

    private fun displayHint(guess: Int, correctTime: Int, remainingAttempts: Int) {
        val hint = when {
            guess > correctTime -> "Your guess is a bit higher."
            guess < correctTime -> "Your guess is a bit lower."
            else -> ""
        }
        println("$hint $remainingAttempts attempts remaining.")
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val game = GuessGameUseCase(dataSource)
    game.playGuessGame()
}