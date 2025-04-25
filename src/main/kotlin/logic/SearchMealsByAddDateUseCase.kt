package logic

import model.MealItem

class SearchMealsByAddDateUseCase(
    private val dataSource: FoodChangeModeDataSource,
    private val dateFormatConverter: DateFormatConverter
) {
    fun getSearchMealsByAddDate(date: String): List<MealItem> {

        val convertDate = dateFormatConverter.convertDate(date)
        val meals = dataSource.getAllMeals().filter { meal -> meal.submitted == convertDate }

        return meals
    }
}