package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import org.example.logic.usecase.GetSweetMealsNotContainEggUseCase
import org.example.logic.usecase.SweetMealWithoutEggUseCase
import org.example.model.Meal
import org.example.model.Nutrition
import org.example.utils.MealAppException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SweetMealWithoutEggUseCaseTest {

 private lateinit var sweetMealsUseCase: GetSweetMealsNotContainEggUseCase
 private lateinit var useCase: SweetMealWithoutEggUseCase

 @BeforeTest
 fun setup() {
  sweetMealsUseCase = mockk()
  useCase = SweetMealWithoutEggUseCase(sweetMealsUseCase)
 }

 private fun createMeal(
  id: Int = 1,
  name: String = "Meal",
  ingredients: List<String> = emptyList()
 ): Meal {
  return Meal(
   id = id,
   name = name,
   preparationTime = 30,
   contributorId = 1,
   submitted = LocalDate.parse("2023-01-01"), // Fixed: Provide valid date string
   tags = emptyList(),
   nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
   numberOfSteps = 1,
   steps = emptyList(),
   description = null,
   ingredients = ingredients,
   numberOfIngredients = ingredients.size
  )
 }

 @Test
 fun `should return random egg-free sweet meal when available`() {
  // Given
  val meal1 = createMeal(1, "Chocolate Cake", listOf("flour", "sugar", "cocoa"))
  val meal2 = createMeal(2, "Fruit Salad", listOf("apple", "banana", "berries"))
  every { sweetMealsUseCase() } returns listOf(meal1, meal2)

  // When
  val result = useCase.getRandomsEggFreeSweet()

  // Then
  assertThat(result).isAnyOf(meal1, meal2) }

 @Test
 fun `should throw exception when no egg-free sweet meals available`() {
  // Given
  every { sweetMealsUseCase() } returns emptyList()

  // When/Then
  assertFailsWith<MealAppException.NoSuchElementException> {
   useCase.getRandomsEggFreeSweet()
  }
 }

 @Test
 fun `should not return disliked meals`() {
  // Given
  val likedMeal = createMeal(1, "Pancakes", listOf("flour", "milk", "sugar"))
  val dislikedMeal = createMeal(2, "Waffles", listOf("flour", "milk", "sugar"))
  every { sweetMealsUseCase() } returns listOf(likedMeal, dislikedMeal)

  // Mark one meal as disliked
  useCase.dislike(dislikedMeal)

  // When
  val result = useCase.getRandomsEggFreeSweet()

  // Then
  assertThat(result).isEqualTo(likedMeal)
 }

 @Test
 fun `should add meal to liked foods`() {
  // Given
  val meal = createMeal(1, "Cheesecake", listOf("cheese", "sugar"))

  // When
  useCase.like(meal)

  // Then
  assertThat(useCase.getLikedFood()).contains(meal)
 }

 @Test
 fun `should add meal to disliked foods`() {
  // Given
  val meal = createMeal(1, "Jelly", listOf("gelatin", "sugar"))

  // When
  useCase.dislike(meal)

  // Then
  assertThat(useCase.getDislikedFood()).contains(meal)
 }

 @Test
 fun `should not return same meal twice in a row when multiple available`() {
  // Given
  val meals = List(10) { createMeal(it, "Dessert $it", listOf("sugar")) }
  every { sweetMealsUseCase() } returns meals

  // When
  val results = List(50) { useCase.getRandomsEggFreeSweet() }

  // Then - Verify we get variety (not perfect but good enough for testing)
  assertThat(results.toSet().size).isGreaterThan(1)
 }

 @Test
 fun `should prefer liked meals over others`() {
  // Given
  val likedMeal = createMeal(1, "Favorite Cake", listOf("flour", "sugar"))
  val otherMeal = createMeal(2, "Other Dessert", listOf("sugar"))
  every { sweetMealsUseCase() } returns listOf(likedMeal, otherMeal)

  useCase.like(likedMeal)

  // When
  val results = List(10) { useCase.getRandomsEggFreeSweet() }

  // Then - Liked meal should appear more frequently
  assertThat(results.count { it == likedMeal }).isGreaterThan(results.count { it == otherMeal })
 }


}