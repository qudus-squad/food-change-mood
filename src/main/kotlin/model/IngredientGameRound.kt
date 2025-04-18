package model

data class IngredientGameRound(
    val mealName: String,
    val options: List<String>,
    val correctIngredient: String,
)