package logic

import model.MealItem
import model.NoMealsFoundException
import utils.Messages.NO_MEALS_FOR_ADD_DATE

class SearchMealsByAddDateUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()
    private val dateFormatConverter = DateFormatConverter()
    fun getSearchMealsByAddDate(date: String): List<MealItem> {

        val convertDate = dateFormatConverter.convertDate(date)
        val meals = meals.filter { it.submitted == convertDate }

        if (meals.isEmpty()) throw NoMealsFoundException("$NO_MEALS_FOR_ADD_DATE $date")

        return meals
    }
}