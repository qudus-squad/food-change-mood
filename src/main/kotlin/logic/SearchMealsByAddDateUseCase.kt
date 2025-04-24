package logic

import model.MealItem
import model.NoMealsFoundException
import utils.Messages.NO_MEALS_FOR_ADD_DATE

class SearchMealsByAddDateUseCase(private val dataSource: FoodChangeModeDataSource) {

    private val dateFormatConverter = DateFormatConverter()
    fun getSearchMealsByAddDate(date: String): List<MealItem> {

        val convertDate = dateFormatConverter.convertDate(date)
        val meals = dataSource.getAllMeals().filter { it.submitted == convertDate }

        if (meals.isEmpty()) throw NoMealsFoundException("$NO_MEALS_FOR_ADD_DATE $date")

        return meals
    }
}