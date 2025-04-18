package logic

import kotlin.random.Random

data class Meal(val name: String, val time: Int)

class GuessGame {
    private val meals = listOf(
        Meal("Pizza", 2),
        Meal("Pasta", 3),
        Meal("Kebab", 4),
        Meal("Kota", 5),
        Meal("Salad", 6)
    )
    private val maxAttempts = 3

    fun playGuessGame() {
        if (!runGuessingRound()) {
            println("Game Over")
        }
    }

    private fun generateRandomMeal(): Meal {
        return meals.random()
    }

    private fun runGuessingRound(): Boolean {
        val selectedMeal = generateRandomMeal()
        println("Guess the preparation time for ${selectedMeal.name} to get prepared (You have $maxAttempts attempts)")

        for (attemptsLeft in maxAttempts downTo 1) {
            val guess = getUserGuess() ?: continue // If null, skip to the next iteration

            if (isCorrectGuess(guess, selectedMeal)) {
                println("Congratulations! Your guess is correct.")
                return true
            } else {
                displayHint(guess, selectedMeal.time, attemptsLeft)
            }
        }

        println("You have run out of attempts. The correct preparation time was ${selectedMeal.time} minutes.")
        return false
    }

    private fun getUserGuess(): Int? {
        print("Enter your guess: ")
        return readlnOrNull()?.toIntOrNull()
    }

    private fun isCorrectGuess(guess: Int, selectedMeal: Meal): Boolean {
        return guess == selectedMeal.time
    }

    private fun displayHint(guess: Int, correctTime: Int, remainingAttempts: Int) {
        val hint = when {
            guess > correctTime -> "Your guess is a bit higher."
            guess < correctTime -> "Your guess is a bit lower."
            else -> "" // Should not happen if isCorrectGuess is checked first
        }
        println("$hint $remainingAttempts attempts remaining.")
    }
}

fun main() {
    val game = GuessGame()
    game.playGuessGame()
}