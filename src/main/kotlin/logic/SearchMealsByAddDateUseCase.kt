package logic

import model.MealItem
import model.NoMealsFoundException
import utils.Messages.NO_MEALS_FOR_ADD_DATE

class SearchMealsByAddDateUseCase(dataSource: FoodChangeModeDataSource) {
    private val meals = dataSource.getAllMeals()
    private val dateFormatConverter = DateFormatConverter() // no dependency inversion -> it is a property not a parameter
    // change the name of the function: Add -> Addition
    fun getSearchMealsByAddDate(date: String): List<MealItem> { // date is not a string, it is a time stamp of Long datatype

        val convertDate = dateFormatConverter.convertDate(date) // clarify & edit "convertDate" - is it a function not a variable
        val meals = meals.filter { it.submitted == convertDate } // clarify & edit "submitted" - what does it mean?

        if (meals.isEmpty()) throw NoMealsFoundException("$NO_MEALS_FOR_ADD_DATE $date") // why throwing an exception
        // exceptions are mandatory when an unusual behavior appear

        return meals
    }
}