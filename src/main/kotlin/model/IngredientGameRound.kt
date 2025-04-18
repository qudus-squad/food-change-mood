package model

// A model to represent a round in an Ingredient Game.
data class IngredientGameRound(
    val mealName: String,
    val options: List<String>,
    val correctIngredient: String,
)