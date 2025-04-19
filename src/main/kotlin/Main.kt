import di.appModule
import logic.*
import model.MealItem
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import utils.Utils.printMealDetails

fun main() {
    startKoin {
        modules(appModule)

    }
    while (true) {
        println("\n=== Main Menu ===")
        println("1 - List Of Healthy Fast Food Meals ")
        println("2 - Iraqi Meals")
        println("3 - Sweets With No Eggs")
        println("4 - I Love Potato")
        println("5 - Meal With More Than '700' Calories")
        println("6 - List Of All Seafood Meals")
        println("7 - Italian Dish For Large Groups")
        println("8 - Gym Helper")
        println("9 - Keto Diet MealS Helper")
        println("10- Easy Food Suggestion")
        println("11- Search Food")
        println("12- Games")
        print("Enter your choice: ")
        when (readlnOrNull()?.trim()) {
            "1" -> getFastHealthyMeals()
            "2" -> getIraqiMeals()
            "3" -> getSweetsWithEggs() //not found
            "4" -> getMealsWithPotatoes()
            "5" -> getMealsWith700Calories()
            "6" -> getSeafoodMeals() // seafood
            "7" -> getItalianFoodForLargeGroups()
            "8" -> getMealsForGymHelper()
            "9" -> getKetoRandomMeal()
            "10" -> getMealsSuggestions() //suggestion
            "11" -> searchFood() // search food by country
            "12" -> foodGames()
        }
    }
}

/////////////////////////////////////// FAST HEALTHY MEALS  ////////////////////////////////////( 0 -> 1 )

fun getFastHealthyMeals() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val getHealthyFastMeals = GetHealthyFastMealsUseCase(dataSource)
    val healthyFastMeals = getHealthyFastMeals.getHealthyFastMeals()
    if (healthyFastMeals.isNotEmpty()) {
        println("healthy fast food meals that can be prepared in 15 minutes or less:")
        healthyFastMeals.forEachIndexed { index, mealItem ->
            println("* Meal number ${index + 1} *")
            printMealDetails(mealItem)
        }
    }
}

/////////////////////////////////////// IRAQI MEALS  ////////////////////////////////////( 0 -> 2 )

fun getIraqiMeals() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val iraqMeals = GetIraqMealsUsingDescription(dataSource).getIraqMeals()

    if (iraqMeals.isEmpty()) println("iraqi meals not found")
    else {
        println("iraqi meals found : ")
        iraqMeals.forEach {
            println("${it.name}: ${it.description}")
        }
    }
}

/////////////////////////////////////// SWEETS WITH NO EGGS  ////////////////////////////////////( 0 -> 3 )

fun getSweetsWithEggs() {}

/////////////////////////////////////// MEALS WITH POTATOES  ////////////////////////////////////( 0 -> 4 )

fun getMealsWithPotatoes() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val potatoMeals = GetRandomTenPotatoMeals(dataSource)
    println("The best potato meals for you:")
    val randomPotatoMeals = potatoMeals.getPotatoMeals()
    randomPotatoMeals.forEach { meal ->
        println("Name: ${meal.name}, Description: ${meal.description}, Preparation Time: ${meal.minutes} minutes")
    }
}

/////////////////////////////////////// MEALS WITH MORE THAN '700' CALORIES  ////////////////////////////////////( 0 -> 5 )

fun getMealsWith700Calories() {

    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val suggester = GetMealWith700CaloriesOrMoreUseCase(dataSource)
    var suggestedMeal = suggester.suggestMeal()
    try {
        while (suggestedMeal != null) {
            println("Suggested Meal: ${suggestedMeal.name} \n${suggestedMeal.description}")
            println("Press 1 for Like or 0 for Dislike")
            val userResponse = readln().toInt()
            when (userResponse) {
                1 -> {
                    printMealDetails(suggestedMeal)
                    break
                }

                0 -> {
                    suggestedMeal = suggester.suggestMeal()
                    if (suggestedMeal == null) {
                        println("No more available meals .")
                    }
                }

                else -> {
                    throw InvalidUserInputException("Invalid Input")
                }
            }
        }
    } catch (e: InvalidUserInputException) {
        println(e.message)
    } catch (e: Exception) {
        println("An unexpected error occurred: ${e.message}")
    }
}

class InvalidUserInputException(message: String) : Exception(message)

///////////////////////////////////////  SEAFOOD MEALS   ////////////////////////////////////( 0 -> 6 )

fun getSeafoodMeals() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val meals = GetSeafoodMealsUseCase(dataSource).getSeafoodMeals()

    meals.forEach { meal ->
        println(meal.name + meal.id)
    }
}

/////////////////////////////////////// ITALIAN MEALS FOR LARGE GROUPS  ////////////////////////////////////( 0 -> 7 )
fun getItalianFoodForLargeGroups() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val mealFetcher = GetItalianMealsForLargeGroup(dataSource)
    val suggestedMeals = mealFetcher.getSuggestedMeals()
    println("Recommended Italian Dishes for Large Gatherings:")
    for (meal in suggestedMeals) {
        println("- ${meal.name}: ${meal.description}")
    }
}
/////////////////////////////////////// GYM HELPER ////////////////////////////////////( 0 -> 8 )

fun getMealsForGymHelper() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val getGymGoerMealsUseCase = GetGymGoerMealsUseCase(dataSource)
    println("Gym Helper: Enter your desired meal parameters")
    print("Target Calories: ")
    val targetCalories = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Target Protein (grams): ")
    val targetProtein = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    if (targetCalories <= 0.0 || targetProtein <= 0.0) {
        println("Please enter valid positive numbers for calories and protein.")
        return
    }
    val suggestions = getGymGoerMealsUseCase.getGymGoerMeals(calories = targetCalories, protein = targetProtein)
    if (suggestions.isEmpty()) {
        println("No meals found matching your criteria (within ±50 calories and protein). Try adjusting your targets.")
    } else {
        println("\nMeal Suggestions for ~$targetCalories calories and ~$targetProtein grams of protein:")
        suggestions.forEachIndexed { index, meal ->
            println("*\n Meal number ${index + 1} *")
            printMealDetails(meal)
        }
    }
}

/////////////////////////////////////// Random KETO DIET MEAL  ////////////////////////////////////( 0 -> 9 )

fun getKetoRandomMeal() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val meal = GetKetoRandomMeal(dataSource).getRandomKetoMealUseCase()
    if(meal == null){
        println("There is No keto meals")
        return
    }
    println("Keto Meal => Name: ${meal.name}, Description: ${meal.description}")
}


/////////////////////////////////////// MEALS SUGGESTION  ////////////////////////////////////( 0 -> 10 )

fun getMealsSuggestions() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()

    val suggestMeals = GetFoodSuggestion(dataSource).suggestEasyMeals()

    suggestMeals.forEach { meal ->
      printMealDetails(meal)

    val easyMeals = GetFoodSuggestion(dataSource).suggestEasyMeals(numberOfSuggest = 10)

    if (easyMeals.isEmpty()) {
        println("\n❗ No easy meals found.")
        return
    }

    println("\n🍽️ ${easyMeals.size} Easy Meals Suggestions:")
    easyMeals.forEachIndexed { index, meal ->
        println("${index + 1}. ${meal.name}")

    }
}

/////////////////////////////////////// SEARCH FOOD MEALS ////////////////////////////////////( 0 -> 11 )

fun searchFood() {
    while (true) {
        println("\n=== Search Food ===")
        println("1-Search Food By Country")
        println("2-Search Food By Add Date")
        println("3-Search Food By Name")
        println("0-Back to Main Menu")
        when (readlnOrNull()?.trim()) {
            "1" -> searchFoodByCountry()
            "2" -> searchFoodByAddDate()
            "3" -> searchFoodByName()
            "0" -> return
            else -> println("Invalid choice. Please try again.")
        }
    }
}

fun searchFoodByCountry() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val countryFoodUseCase = CountryFoodUseCase(dataSource)

    if (iraqMeals.isEmpty()) println("iraqi meals not found")
    else {
        println("iraqi meals found : ")
        iraqMeals.forEach { meal ->
            printMealDetails(meal)

    while (true) {
        println("\nPlease enter a country name (or type 0 to go back):")
        val input = readlnOrNull()?.trim().orEmpty()

        if (input == "0") {
            println("Returning to the main menu.")
            break
        }

        try {
            val meals = countryFoodUseCase.getRandomlyCountryMeals(countryName = input, numOfRandomlyMeals = 20)
            println("Meals for \"$input\":")
            meals.forEach { println("- ${it.name}") }
            break
        } catch (e: InvalidCountryException) {
            println("Invalid input: ${e.message}")
        } catch (e: NoMealsFoundException) {
            println("${e.message}")

        }

        println("Please try again.\n")
    }
}

fun searchFoodByAddDate() {
    println("Enter Meal Submitted Date in : YY-MM-DD ")
    val inputDate = readlnOrNull()?.trim().toString()
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val searchFoodByAddDate = SearchFoodByAddDate(dataSource)
    val result: List<MealItem> = searchFoodByAddDate.getSearchMealsByAddDate(inputDate)
    println(result)
}

fun searchFoodByName() {
    val foodSearchAlgorithm = FoodSearchAlgorithm()
    val searchedName = readlnOrNull()?.trim() ?: ""
    val result = foodSearchAlgorithm.searchFoodFromCsv(searchedName)
    result.take(10).forEach { MealAndId -> println(MealAndId) }
}

/////////////////////////////////////// FOOD GAMES ////////////////////////////////////( 0 -> 12 )

fun foodGames() {
    while (true) {
        println("\n=== Food Games ===")
        println("1-Guess Game")
        println("2-Ingredient Game")
        println("0-Back to Main Menu")
        when (readlnOrNull()?.trim()) {
            "1" -> startGuessGame()
            "2" -> startIngredientGame()
            "0" -> return
            else -> println("Invalid choice. Please try again.")
        }
    }
}

fun startGuessGame() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val game = GuessGameUseCase(dataSource)
    game.playGuessGame()
}

fun startIngredientGame() {
    val dataSource: FoodChangeModeDataSource = getKoin().get()
    val gameUseCase = IngredientGameUseCase(dataSource)
    gameUseCase.resetGame()
    while (true) {
        try {
            val round = gameUseCase.startNewRound()
            println("Guess the ingredient for: ${round.mealName}")
            round.options.forEachIndexed { index, option ->
                println("${index + 1}. $option")
            }
            val guessIndex = readlnOrNull()?.toIntOrNull()?.let { it - 1 } ?: -1
            if (guessIndex !in 0..2) {
                println("Invalid choice")
                continue
            }
            val guess = round.options[guessIndex]
            when (val result = gameUseCase.processGuess(guess, round.correctIngredient)) {
                is GameResult.Correct -> {
                    println("Correct! Points: ${result.currentPoints}, Correct Answers: ${result.correctAnswers}")
                }

                is GameResult.Incorrect -> {
                    println("Incorrect! Game Over. Final Points: ${result.currentPoints}, Correct Answers: ${result.correctAnswers}")
                    break
                }

                is GameResult.GameCompleted -> {
                    println("Congratulations! Game Completed. Final Points: ${result.currentPoints}")
                    break
                }
            }
        } catch (e: InsufficientMealsException) {
            println("You've answered all available meals!")
            break
        }
    }
}