package logic

import model.MealItem

interface FoodChangeModeDataSource {
    fun getAllMeals(): List<MealItem>
}