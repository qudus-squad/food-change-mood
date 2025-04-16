package logic

import model.MealItem

interface FoodMenu {

    fun filterPreparedTimeMeals(mealItems: List<MealItem>, preparedTime: Int): List<MealItem>
    fun filterTotalFatMeals(mealItems: List<MealItem>, totalFat: Int): List<MealItem>
    fun filterSaturatedFatMeals(mealItems: List<MealItem>, saturatedFat: Int): List<MealItem>
    fun filterCarbohydrateMeals(mealItems: List<MealItem>, carbohydrate: Int): List<MealItem>
    fun filterProteinMeals(mealItems: List<MealItem>, protein: Int): List<MealItem>
    fun filterCaloriesMeals(mealItems: List<MealItem>, calories: Int): List<MealItem>
    fun filterMealsByCountry(mealItems: List<MealItem>, country: String): List<MealItem>


    fun getMealsByName(mealItems: List<MealItem>, foodName: String): List<MealItem>
    fun getMealsByIngredientsNumber(mealItems: List<MealItem>, ingredientsNumbers: Int): List<MealItem>
    fun getMealsByStepsNumber(mealItems: List<MealItem>, stepsNumber: Int): List<MealItem>
    fun getMealsByIngredient(mealItems: List<MealItem>, ingredientsName: String): List<MealItem>
    fun getMealsByIngredients(mealItems: List<MealItem>, ingredients: List<String>):List<MealItem>
    fun getMealsByDate(mealItems: List<MealItem>, date: String): List<MealItem>

}