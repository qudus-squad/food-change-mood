package model

data class IngredientGameRound(
    val mealName: String,
    val optionsOfIngredients: List<String>,
    val correctIngredient: String,
)