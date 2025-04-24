package logic

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.Messages.NO_MORE_HIGH_CALORIE_MEALS

class GetHighCalorieMealsUseCaseTest {
 private lateinit var dataSource: FoodChangeModeDataSource
 private lateinit var getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase

 @BeforeEach
 fun setup() {
  dataSource = mockk(relaxed = true)
  getHighCalorieMealsUseCase = GetHighCalorieMealsUseCase(dataSource)
 }

 private fun getMealItems() = listOf(
  MealItem(
   id = 1,
   name = "Quinoa Salad",
   minutes = 15,
   contributorId = 101,
   submitted = LocalDate.parse("2021-03-10"),
   tags = listOf("healthy", "vegetarian", "low-calorie"),
   nutrition = Nutrition(
    calories = 350.0,
    totalFat = 10.0,
    sugar = 2.0,
    sodium = 150.0,
    protein = 8.0,
    saturatedFat = 1.0,
    carbohydrates = 45.0
   ),
   stepNumbers = 2,
   steps = listOf("Cook quinoa", "Mix with veggies"),
   description = "Light and refreshing salad.",
   ingredients = listOf("quinoa", "cucumber", "tomato", "olive oil", "lemon juice"),
   ingredientNumbers = 5
  ),
  MealItem(
   id = 2,
   name = "Steak with Fries",
   minutes = 25,
   contributorId = 102,
   submitted = LocalDate.parse("2022-05-18"),
   tags = listOf("main", "high-protein", "high-calorie"),
   nutrition = Nutrition(
    calories = 950.0,
    totalFat = 60.0,
    sugar = 1.0,
    sodium = 800.0,
    protein = 45.0,
    saturatedFat = 25.0,
    carbohydrates = 50.0
   ),
   stepNumbers = 3,
   steps = listOf("Grill steak", "Fry potatoes", "Serve hot"),
   description = "Hearty and satisfying meal.",
   ingredients = listOf("steak", "potatoes", "salt", "pepper", "oil"),
   ingredientNumbers = 5
  ),
  MealItem(
   id = 3,
   name = "Chicken Alfredo Pasta",
   minutes = 30,
   contributorId = 103,
   submitted = LocalDate.parse("2020-11-02"),
   tags = listOf("pasta", "comfort-food", "high-calorie"),
   nutrition = Nutrition(
    calories = 820.0,
    totalFat = 45.0,
    sugar = 3.0,
    sodium = 700.0,
    protein = 30.0,
    saturatedFat = 18.0,
    carbohydrates = 60.0
   ),
   stepNumbers = 4,
   steps = listOf("Boil pasta", "Cook chicken", "Make Alfredo sauce", "Mix and serve"),
   description = "Creamy and flavorful.",
   ingredients = listOf("pasta", "chicken", "cream", "cheese", "garlic"),
   ingredientNumbers = 5
  )
 )

 @Test
 fun `should return meal When the calories more than or equal to 700`() {
  // Given

  val suggestMeal = getMealItems()
  every { dataSource.getAllMeals() } returns suggestMeal
  // When

  val result = getHighCalorieMealsUseCase.suggestMeal()
 // Then

  result.id shouldBe 2
  result.nutrition.calories shouldBe 950.0
 }

 @Test
 fun `should throws NO_MORE_HIGH_CALORIE_MEALS when all high calorie meals are already suggested`() {
// Given

  val suggestMeal = getMealItems()
  every { dataSource.getAllMeals() } returns suggestMeal
// When & Then

  getHighCalorieMealsUseCase.suggestMeal()
  getHighCalorieMealsUseCase.suggestMeal()

  val exception = shouldThrow<NoSuchElementException> {
   getHighCalorieMealsUseCase.suggestMeal()
  }
  exception.message shouldBe NO_MORE_HIGH_CALORIE_MEALS
 }

 @Test
 fun `should throw NO_MORE_HIGH_CALORIE_MEALS when there are no high calorie meals`() {
  // Given

  val lowCalorieMeals = getMealItems().filter { it.nutrition.calories < 700.0 }
  every { dataSource.getAllMeals() } returns lowCalorieMeals
// When & Then

  val exception = shouldThrow<NoSuchElementException> {
   getHighCalorieMealsUseCase.suggestMeal()
  }
  exception.message shouldBe NO_MORE_HIGH_CALORIE_MEALS
 }

 @Test
 fun `should throw exception when meal calories are less than default 700`() {
// Given

  val suggestMeal = getMealItems()
   .filter { it.nutrition.calories < 700.0 }
  every { dataSource.getAllMeals() } returns suggestMeal
 // When & Then

  val exception = shouldThrow<NoSuchElementException> {
   getHighCalorieMealsUseCase.suggestMeal()
  }
  exception.message shouldBe NO_MORE_HIGH_CALORIE_MEALS
 }

}

