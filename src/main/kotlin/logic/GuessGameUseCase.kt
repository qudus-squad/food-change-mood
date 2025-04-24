package logic
import model.MealItem

class GuessGameUseCase(
    dataSource: FoodChangeModeDataSource
) {
    val meals = dataSource.getAllMeals()
    val maxAttempts = 3

    fun hasMeals() : Boolean = meals.isNotEmpty()

    private fun generateRandomMeal(): MealItem {
        return meals.random()
    }

    fun runGuessingRound(guess : Int): Boolean {
        val selectedMeal = generateRandomMeal()

        if (isCorrectGuess(guess, selectedMeal)) {
            return true
        } else {
            displayHint(guess, selectedMeal.minutes)
        }
        return false
    }

    fun getUserGuess(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun isCorrectGuess(guess: Int, selectedMeal: MealItem): Boolean {
        return guess == selectedMeal.minutes
    }

    private fun displayHint(guess: Int, correctTime: Int) : String {
        return when {
            guess > correctTime -> "Your guess is a bit higher."
            guess < correctTime -> "Your guess is a bit lower."
            else -> ""
        }
    }
}