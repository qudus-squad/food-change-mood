package logic

import model.MealItem

interface MealRepository{
    fun getAllMeals() : List<MealItem>
}
