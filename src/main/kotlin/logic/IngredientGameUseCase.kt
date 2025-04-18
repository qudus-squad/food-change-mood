package logic

import model.IngredientGameRound

class IngredientGameUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()
    private var points: Int = 0
    private var correctAnswers: Int = 0
    private val playedMeals = mutableSetOf<Int>()

    fun startNewRound(): IngredientGameRound {
        val availableMeals = meals
            .filter { it.ingredients.isNotEmpty() }
            .filter { it.id !in playedMeals }
        if (availableMeals.isEmpty()) {
            throw InsufficientMealsException("No more meals available to play the game")
        }

        val selectedMeal = availableMeals.random()
        playedMeals.add(selectedMeal.id)
        val correctIngredient = selectedMeal.ingredients.random()
        val otherIngredients = meals
            .filter { it.id != selectedMeal.id }
            .flatMap { it.ingredients }
            .distinct()
            .filter { it != correctIngredient }

        if (otherIngredients.size < 2) {
            throw InsufficientMealsException("Not enough ingredients to create a game round")
        }
        val wrongIngredients = otherIngredients.shuffled().take(2)
        val options = (listOf(correctIngredient) + wrongIngredients).shuffled()
        return IngredientGameRound(
            mealName = selectedMeal.name,
            options = options,
            correctIngredient = correctIngredient
        )
    }

    fun processGuess(userGuess: String, correctIngredient: String): GameResult {
        if (userGuess == correctIngredient) {
            points += POINTS_PER_CORRECT_ANSWER
            correctAnswers++
            if (correctAnswers >= MAX_CORRECT_ANSWERS) {
                return GameResult.GameCompleted(points)
            }
            return GameResult.Correct(points, correctAnswers)
        } else {
            return GameResult.Incorrect(points, correctAnswers)
        }
    }

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

sealed class GameResult {
    data class Correct(val currentPoints: Int, val correctAnswers: Int) : GameResult()

    data class Incorrect(val currentPoints: Int, val correctAnswers: Int) : GameResult()

    data class GameCompleted(val currentPoints: Int) : GameResult()
}

class InsufficientMealsException(message: String) : Exception(message)
