package logic

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import model.MealItem
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetHealthyFastMealsUseCaseTest {

 private lateinit var dataSource: FoodChangeModeDataSource
 private lateinit var useCase: GetHealthyFastMealsUseCase

 @BeforeEach
 fun setup() {
  dataSource = mockk()
 }
 @Test
 fun `getHealthyFastMeals should return empty list when validMeal is empty`() {
  // given
  every { dataSource.getAllMeals() } returns emptyList()
  useCase = GetHealthyFastMealsUseCase(dataSource)
  // when
  val result = useCase.getHealthyFastMeals()
  // then
  result.shouldBeEmpty()
 }

 @Test
 fun `returns empty list when no meal meets at least 2 nutrient criteria`() {
  // Given
  val date = LocalDate(2025, 4, 1)
  every { dataSource.getAllMeals() } returns listOf(
   // Meal 1
   MealItem(
    id = 1,
    name = "Meal 1",
    minutes = 16,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 1.0,
     saturatedFat = 10.0,
     carbohydrates = 30.0,
     calories = 0.0,
     sugar = 0.0,
     sodium = 0.0,
     protein = 0.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 2
   MealItem(
    id = 2,
    name = "Meal 2",
    minutes = 16,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 10.0,
     saturatedFat = 1.0,
     carbohydrates = 30.0,
     calories = 0.0,
     sugar = 0.0,
     sodium = 0.0,
     protein = 0.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 3
   MealItem(
    id = 3,
    name = "Meal 3",
    minutes = 16,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 10.0,
     saturatedFat = 10.0,
     carbohydrates = 1.0,
     calories = 0.0,
     sugar = 0.0,
     sodium = 0.0,
     protein = 0.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   )
  )
  val useCase = GetHealthyFastMealsUseCase(dataSource)

  // When
  val result = useCase.getHealthyFastMeals()

  // Then
  result.shouldBeEmpty()
 }

 @Test
 fun `GetHealthyFastMeals should return empty list when all meals exceed 15 minutes preparation time`() {
  // Given
  val date = LocalDate(2025, 4, 1)
  every { dataSource.getAllMeals() } returns listOf(
   // Meal 1
   MealItem(
    id = 1,
    name = "Meal 1",
    minutes = 16,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
        totalFat = 10.0,
        saturatedFat = 5.0,
        carbohydrates = 30.0,
        calories = 10.0,
        sugar = 10.0,
        sodium = 10.0,
        protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 2
   MealItem(
    id = 2,
    name = "Meal 2",
    minutes = 17,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 10.0,
     saturatedFat = 5.0,
     carbohydrates = 30.0,
     calories = 10.0,
     sugar = 10.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 3
   MealItem(
    id = 3,
    name = "Meal 3",
    minutes = 18,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 10.0,
     saturatedFat = 5.0,
     carbohydrates = 30.0,
     calories = 10.0,
     sugar = 10.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   )
  )
  useCase = GetHealthyFastMealsUseCase(dataSource)

  // When
  val result = useCase.getHealthyFastMeals()

  // Then
  result.shouldBeEmpty()
 }

 @Test
 fun `should return top 5 scoring meals when more than five are eligible`() {
  // Given
  val date = LocalDate(2025, 4, 1)
  every { dataSource.getAllMeals() } returns listOf(
   // Meal 1
   MealItem(
     id = 1,
     name = "Meal 1",
     minutes = 10,
     contributorId = 100,
     submitted = date,
     tags = listOf("test", "slow"),
     nutrition = Nutrition(
      totalFat = 10.0,
      saturatedFat = 5.0,
      carbohydrates = 30.0,
      calories = 10.0,
      sugar = 10.0,
      sodium = 10.0,
      protein = 10.0
     ),
     stepNumbers = 3,
     steps = listOf("Step 1", "Step 2", "Step 3"),
     description = "Takes too long",
     ingredients = listOf("Ingredient1", "Ingredient2"),
     ingredientNumbers = 2
    ),
   // Meal 2
   MealItem(
   id = 2,
   name = "Meal 2",
   minutes = 10,
   contributorId = 100,
   submitted = date,
   tags = listOf("test", "slow"),
   nutrition = Nutrition(
    totalFat = 9.0,
    saturatedFat = 4.5,
    carbohydrates = 31.0,
    calories = 8.5,
    sugar = 7.5,
    sodium = 10.0,
    protein = 10.0
   ),
   stepNumbers = 3,
   steps = listOf("Step 1", "Step 2", "Step 3"),
   description = "Takes too long",
   ingredients = listOf("Ingredient1", "Ingredient2"),
   ingredientNumbers = 2
  ),
   // Meal 3
   MealItem(
    id = 3,
    name = "Meal 3",
    minutes = 10,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 12.0,
     saturatedFat = 5.5,
     carbohydrates = 31.0,
     calories = 12.0,
     sugar = 6.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 4
   MealItem(
    id = 4,
    name = "Meal 4",
    minutes = 10,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 9.0,
     saturatedFat = 5.5,
     carbohydrates = 32.0,
     calories = 11.0,
     sugar = 10.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 5
   MealItem(
    id = 5,
    name = "Meal 5",
    minutes = 10,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 10.0,
     saturatedFat = 5.0,
     carbohydrates = 30.0,
     calories = 10.0,
     sugar = 10.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 6
   MealItem(
    id = 6,
    name = "Meal 6",
    minutes = 10,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 15.0,
     saturatedFat = 4.0,
     carbohydrates = 25.0,
     calories = 10.0,
     sugar = 10.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   ),
   // Meal 7
   MealItem(
    id = 7,
    name = "Meal 7",
    minutes = 10,
    contributorId = 100,
    submitted = date,
    tags = listOf("test", "slow"),
    nutrition = Nutrition(
     totalFat = 1.0,
     saturatedFat = 5.0,
     carbohydrates = 20.0,
     calories = 10.0,
     sugar = 10.0,
     sodium = 10.0,
     protein = 10.0
    ),
    stepNumbers = 3,
    steps = listOf("Step 1", "Step 2", "Step 3"),
    description = "Takes too long",
    ingredients = listOf("Ingredient1", "Ingredient2"),
    ingredientNumbers = 2
   )
   )
  useCase = GetHealthyFastMealsUseCase(dataSource)
  // When
    val result = useCase.getHealthyFastMeals()
  // Then
   result.shouldHaveSize(5)
  result.map { meal -> meal.name }.shouldContainExactlyInAnyOrder(
   "Meal 2", "Meal 5", "Meal 1", "Meal 4", "Meal 7"
  )
 }

}
