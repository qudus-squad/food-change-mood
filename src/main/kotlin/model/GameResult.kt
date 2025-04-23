package model

sealed class GameResult {
    data class Correct(val currentPoints: Int, val correctAnswers: Int) : GameResult()

    data class Incorrect(val currentPoints: Int, val correctAnswers: Int) : GameResult()

    data class GameCompleted(val currentPoints: Int) : GameResult()
}