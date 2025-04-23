package logic

import model.MealItem
import model.NoMealsFoundException
import utils.ListUtils.orThrowIfEmpty
import utils.Messages.NO_MEALS_FOR_LARGE_GROUP_FOND_FOR_COUNTRY
import utils.Strings.FOR_LARGE_GROUP
import utils.Strings.ITALIAN

class GetItalianMealsForLargeGroupOfPeopleUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getItalianMealsForLargeGroupOfPeople(
        countryName: String = ITALIAN, mealsNumber: Int = 10
    ): List<MealItem> {
        val italianMeals = filterMealsByCountry(dataSource.getAllMeals(), countryName)
        return italianMeals.filter { isMealForLargeGroup(it) }
            .orThrowIfEmpty { NoMealsFoundException("$NO_MEALS_FOR_LARGE_GROUP_FOND_FOR_COUNTRY $countryName") }
            .shuffled()
            .take(mealsNumber)
    }

    private fun filterMealsByCountry(mealItems: List<MealItem>, countryName: String): List<MealItem> {
        return mealItems.filter { meal ->
            meal.description.contains(countryName, ignoreCase = true) || meal.name.contains(
                countryName,
                ignoreCase = true
            ) || meal.tags.contains(countryName)
        }
    }

    private fun isMealForLargeGroup(meal: MealItem): Boolean {
        return meal.tags.contains(FOR_LARGE_GROUP)
    }
}