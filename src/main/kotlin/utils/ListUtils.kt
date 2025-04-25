package utils

import model.MealItem
import kotlin.random.Random

object ListUtils {

    inline fun <T> List<T>.orThrowIfEmpty(exception: () -> Throwable): List<T> {
        return takeIf { it.isNotEmpty() } ?: throw exception()
    }

    fun List<MealItem>.getRandomRangeFromList(numberOfMeals: Int): List<MealItem> {
        val firstNumber = Random.nextInt(0, this.size - 1)
        val secondNumber = firstNumber + numberOfMeals
        return this.subList(firstNumber, secondNumber)
    }
}
