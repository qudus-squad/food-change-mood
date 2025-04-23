package logic

import model.MealItem
import utils.Strings.FOR_LARGE_GROUP
import utils.Strings.ITALIAN

class GetItalianMealsForLargeGroupOfPeopleUseCase(private val dataSource: FoodChangeModeDataSource) {


    fun getItalianMealsForLargeGroupOfPeople(countryName: String = ITALIAN): List<MealItem> {
        val italianMeals = filterMealsByCountry(dataSource.getAllMeals(), countryName)
        return italianMeals.filter { meal ->
            isMealForLargeGroup(meal)
        }
    }

    private fun filterMealsByCountry(mealItems: List<MealItem>, countryName: String): List<MealItem> {
        return mealItems.filter { meal ->
            meal.description.contains(countryName, ignoreCase = true)
                    || meal.name.contains(countryName, ignoreCase = true)
                    || meal.tags.contains(countryName)
        }
    }

    private fun isMealForLargeGroup(meal: MealItem): Boolean {
        return meal.tags.contains(FOR_LARGE_GROUP)
    }
}