package logic

import model.MealItem

class GuessGameUseCase(
    dataSource: FoodChangeModeDataSource
) {
    val meals = dataSource.getAllMeals()
    val maxAttempts = 3 // hard coded, maxAttemptsCount
    val selectedMeal = generateRandomMeal()

    fun hasMeals(): Boolean = meals.isNotEmpty()

    private fun generateRandomMeal(): MealItem {
        return meals.random()
    }
    // rearrange the functions
    // guessedPreparationTime
    fun runGuessingRound(guess: Int): Boolean {

        if (isCorrectGuess(guess, selectedMeal)) {
            return true
        } else {
            displayHint(guess)
        }
        return false
    }

    fun getUserGuess(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun isCorrectGuess(guess: Int, selectedMeal: MealItem): Boolean {
        return guess == selectedMeal.minutes
    }

    fun displayHint(guess: Int): String { // replace this with if statement
        return when {
            guess > selectedMeal.minutes -> "Your guess is a bit higher." // hard coded
            guess < selectedMeal.minutes -> "Your guess is a bit lower."
            else -> ""
        }
    }
}