package logic

import model.MealItem

class GetItalianMealsForLargeGroupOfPeopleUseCase(private val dataSource: FoodChangeModeDataSource) {

    fun getItalianMealsForLargeGroupOfPeople(
        countryName: String = ITALIAN, numberOfMeals: Int = DEFAULT_NUMBER_OF_MEALS
    ): List<MealItem> {
        val italianMeals = filterMealsByCountry(dataSource.getAllMeals(), countryName)
        return italianMeals.filter { meal -> isMealForLargeGroup(meal) }
            .take(numberOfMeals)
    }

    private fun filterMealsByCountry(mealItems: List<MealItem>, countryName: String): List<MealItem> {
        return mealItems.filter { meal ->
            meal.description.contains(countryName, ignoreCase = true) || meal.name.contains(
                countryName,
                ignoreCase = true
            ) || meal.mealTags.contains(countryName)
        }
    }

    private fun isMealForLargeGroup(meal: MealItem): Boolean {
        return meal.mealTags.contains(FOR_LARGE_GROUP)
    }

    companion object {
        private const val DEFAULT_NUMBER_OF_MEALS = 10
        const val FOR_LARGE_GROUP = "for-large-groups"
        const val ITALIAN = "italian"
    }

}