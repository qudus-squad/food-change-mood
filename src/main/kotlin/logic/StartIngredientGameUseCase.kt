package logic

import model.GameResult
import model.IngredientGameRound
import model.NotEnoughMealsException

class StartIngredientGameUseCase(private val dataSource: FoodChangeModeDataSource) {

    private var points: Int = 0
    private var correctAnswers: Int = 0
    private val playedMeals = mutableSetOf<Int>()

    fun startNewRound(): IngredientGameRound {
        val availableMeals = dataSource.getAllMeals()
            .filter { meal -> meal.ingredients.isNotEmpty() }
            .filter { meal -> meal.id !in playedMeals }

        if (availableMeals.isEmpty()) {
            throw NotEnoughMealsException(NOT_ENOUGH_MEALS_EXCEPTION_MESSAGE)
        }

        val selectedMeal = availableMeals.random()
        playedMeals.add(selectedMeal.id)

        val correctIngredient = selectedMeal.ingredients.random()
        val otherIngredients = dataSource.getAllMeals()
            .filter { meal -> meal.id != selectedMeal.id }
            .flatMap { meal -> meal.ingredients }
            .distinct()
            .filter { ingredient -> ingredient != correctIngredient }

        if (otherIngredients.size < MINIMUM_INGREDIENTS_SIZE) {
            throw NotEnoughMealsException(NOT_ENOUGH_WRONG_INGREDIENTS_EXCEPTION_MESSAGE)
        }

        val wrongIngredients = otherIngredients.shuffled().take(NUMBER_OF_WRONG_INGREDIENTS)
        val options = (listOf(correctIngredient) + wrongIngredients).shuffled()
        return IngredientGameRound(
            mealName = selectedMeal.name,
            optionsOfIngredients = options,
            correctIngredient = correctIngredient
        )
    }

    fun processGuess(userGuess: String, correctIngredient: String): GameResult {
        if (userGuess == correctIngredient) {
            points += POINTS_PER_CORRECT_ANSWER
            correctAnswers++
            if (correctAnswers >= MAXIMUM_CORRECT_ANSWERS) {
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
        private const val MAXIMUM_CORRECT_ANSWERS = 15
        private const val MINIMUM_INGREDIENTS_SIZE = 2
        private const val NUMBER_OF_WRONG_INGREDIENTS = 3
        private const val NOT_ENOUGH_MEALS_EXCEPTION_MESSAGE = "Not enough meals to start a round"
        private const val NOT_ENOUGH_WRONG_INGREDIENTS_EXCEPTION_MESSAGE =
            "Not enough wrong ingredients to generate options"
    }
}

