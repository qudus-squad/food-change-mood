import java.time.LocalDate

fun getHealthyFastMeals(meals: List<MealItem>): List<MealItem> {
    // Filter meals that are fast-food, have prep time <= 15, and have valid nutrition
    val validMeals = meals.filter { meal ->
        meal.prepTime <= 15 &&
                "fast-food" in meal.categories &&
                meal.nutrition != null
    }

    if (validMeals.isEmpty()) {
        println("No valid meals found (prepTime <= 15, fast-food, non-null nutrition).")
        return emptyList()
    }

    // Calculate percentiles - Lowest 20%
    val thresholdPercentile = 0.2
    val threshold = maxOf(1, (validMeals.size * thresholdPercentile).toInt())

    // Sort meals by each nutritional value
    val sortedByTotalFat = validMeals.sortedBy { it.nutrition!!.totalFat }
    val sortedBySaturatedFat = validMeals.sortedBy { it.nutrition!!.saturatedFat }
    val sortedByCarbohydrates = validMeals.sortedBy { it.nutrition!!.carbohydrates }

    // Get meals in the lowest 20% for each nutritional value
    val lowestTotalFat = sortedByTotalFat.take(threshold).toSet()
    val lowestSaturatedFat = sortedBySaturatedFat.take(threshold).toSet()
    val lowestCarbohydrates = sortedByCarbohydrates.take(threshold).toSet()

    // Score meals based on presence in lowest sets
    val scoredMeals = validMeals.map { meal ->
        var score = 0
        if (meal in lowestTotalFat) score += 1
        if (meal in lowestSaturatedFat) score += 1
        if (meal in lowestCarbohydrates) score += 1
        meal to score
    }

    // Return meals with the highest score (at least in two of the three lowest sets)
    val maxScore = scoredMeals.maxOfOrNull { it.second } ?: 0
    return scoredMeals
        .filter { it.second == maxScore && it.second >= 2 }
        .map { it.first }
        .sortedBy { it.nutrition!!.totalFat + it.nutrition!!.saturatedFat + it.nutrition!!.carbohydrates }
}