package data

import logic.FoodMenu
import model.MealItem

class DataSource : FoodMenu {
    override fun filterPreparedTimeMeals(mealItems: List<MealItem>, preparedTime: Int): List<MealItem> {
        return mealItems.filter { meal ->
            preparedTime >= meal.minutes
        }
    }

    override fun filterTotalFatMeals(mealItems: List<MealItem>, totalFat: Int): List<MealItem> {
        return mealItems.filter { meal ->
            totalFat >= meal.nutrition.totalFat
        }
    }

    override fun filterSaturatedFatMeals(mealItems: List<MealItem>, saturatedFat: Int): List<MealItem> {
        return mealItems.filter { meal ->
            saturatedFat >= meal.nutrition.saturatedFat
        }
    }

    override fun filterCarbohydrateMeals(mealItems: List<MealItem>, carbohydrate: Int): List<MealItem> {
        return mealItems.filter { meal ->
            carbohydrate >= meal.nutrition.carbohydrates
        }
    }

    override fun filterProteinMeals(mealItems: List<MealItem>, protein: Int): List<MealItem> {
        return mealItems.filter { meal ->
            protein >= meal.nutrition.protein
        }
    }

    override fun filterCaloriesMeals(mealItems: List<MealItem>, calories: Int): List<MealItem> {
        return mealItems.filter { meal ->
            calories >= meal.nutrition.calories
        }
    }

    override fun filterMealsByCountry(mealItems: List<MealItem>, country: String): List<MealItem> {
        return mealItems.filter { meal ->
            meal.tags.any { tag ->
                tag.equals(country, ignoreCase = true)
            }
        }
    }

    override fun getMealsByName(mealItems: List<MealItem>, foodName: String): List<MealItem> {
        return mealItems.filter { meal ->
            meal.name.contains(foodName, ignoreCase = true)
        }
    }

    override fun getMealsByIngredientsNumber(mealItems: List<MealItem>, ingredientsNumbers: Int): List<MealItem> {
        return mealItems.filter { meal ->
            ingredientsNumbers >= meal.ingredientNumbers
        }
    }

    override fun getMealsByStepsNumber(mealItems: List<MealItem>, stepsNumber: Int): List<MealItem> {
        return mealItems.filter { meal ->
            stepsNumber >= meal.stepNumbers
        }
    }

    override fun getMealByIngredient(mealItems: List<MealItem>, ingredientsName: String): MealItem {
        return mealItems.first { meal ->
            meal.ingredients.any { tag ->
                tag.equals(ingredientsName, ignoreCase = true)
            }
        }
    }


    override fun getMealByIngredients(mealItems: List<MealItem>, ingredients: List<String>): MealItem {
        return mealItems.first { meal ->
            ingredients.any { ingredient ->
                ingredient.equals(ingredient, ignoreCase = true)

            }
        }
    }

    override fun getMealsByDate(mealItems: List<MealItem>, date: String): List<MealItem> {
        return mealItems.filter { meal ->
            meal.submitted.equals(date, ignoreCase = true)
        }
    }
}