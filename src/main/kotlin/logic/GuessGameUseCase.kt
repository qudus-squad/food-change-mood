package logic

import model.MealItem

class GuessGameUseCase(
    private val dataSource: FoodChangeModeDataSource
) {
    val selectedMeal = generateRandomMeal()
    fun playGuessGame() : Boolean {
        if(!hasMeals()) {
            return false
        }
        for(attempt in 1 .. MAXIMUM_ATTEMPTS_COUNT) {
            val guess = getUserGuess() ?: continue
            if(!runGuessingRound(guess)) {
                displayHint(guess)
            } else {
                return true
            }
        }
        return false
    }

    private fun hasMeals(): Boolean = dataSource.getAllMeals().isNotEmpty()

    private fun generateRandomMeal(): MealItem {
        return dataSource.getAllMeals().random()
    }

    fun getUserGuess(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun runGuessingRound(guess: Int): Boolean {
        return when {
            isCorrectGuess(guess) -> true
            else -> false
        }
    }

    private fun displayHint(guess: Int): String { // replace this with if statement
        return if(guess > selectedMeal.preparationTimeInMinutes) {
            YOUR_GUESS_IS_HIGHER
        } else {
            YOUR_GUESS_IS_LOWER
        }
    }

    private fun isCorrectGuess(guess: Int): Boolean {
        return guess == selectedMeal.preparationTimeInMinutes
    }

    companion object {
        private const val MAXIMUM_ATTEMPTS_COUNT = 3
        private const val YOUR_GUESS_IS_HIGHER = "Your guess is a bit higher than the actual number"
        private const val YOUR_GUESS_IS_LOWER = "Your guess is a bit lower than the actual number"
    }
}