package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.KetoDietMealHelperUseCase
import org.example.model.Meal
import org.example.model.Nutrition
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class KetoDietMealHelperUseCaseTest {

 private lateinit var mealRepository: MealRepository
 private lateinit var useCase: KetoDietMealHelperUseCase

 @BeforeTest
 fun setup() {
  mealRepository = mockk()
  useCase = KetoDietMealHelperUseCase(mealRepository)
 }

 private fun createMeal(
  id: Int = 1,
  name: String = "Meal",
  nutrition: Nutrition = Nutrition(
   calories = 2000.0,
   totalFat = 156.0,    // 70% of 2000 calories
   protein = 75.0,      // 15% of 2000 calories
   carbohydrates = 25.0, // 5% of 2000 calories
   sugar = 10.0,         // Added missing parameter
   sodium = 500.0,       // Added missing parameter
   saturatedFat = 50.0   // Added missing parameter
  )
 ): Meal {
  return Meal(
   id = id,
   name = name,
   preparationTime = 30,
   contributorId = 1,
   submitted = LocalDate.parse("2023-01-01"),
   tags = emptyList(),
   nutrition = nutrition,
   numberOfSteps = 1,
   steps = emptyList(),
   description = null,
   ingredients = emptyList(),
   numberOfIngredients = 0
  )
 }

 @Test
 fun `should return keto-friendly meal when available`() {
  // Given
  val ketoMeal = createMeal()
  every { mealRepository.getMeals() } returns listOf(ketoMeal)

  // When
  val result = useCase()

  // Then
  assertThat(result).isEqualTo(ketoMeal)
 }

 @Test
 fun `should throw exception when no keto-friendly meals available`() {
  // Given
  val nonKetoMeal = createMeal(nutrition = Nutrition(
   calories = 2000.0,
   totalFat = 50.0,
   protein = 150.0,
   carbohydrates = 200.0,
   sugar = 100.0,
   sodium = 1000.0,
   saturatedFat = 20.0
  ))
  every { mealRepository.getMeals() } returns listOf(nonKetoMeal)

  // When/Then
  assertFailsWith<NoSuchElementException> {
   useCase()
  }
 }

 @Test
 fun `should not return disliked meals`() {
  // Given
  val likedMeal = createMeal(1, "Good Keto Meal")
  val dislikedMeal = createMeal(2, "Bad Keto Meal")
  every { mealRepository.getMeals() } returns listOf(likedMeal, dislikedMeal)

  useCase.dislike(dislikedMeal)

  // When
  val result = useCase()

  // Then
  assertThat(result).isEqualTo(likedMeal)
 }

 @Test
 fun `should calculate percentages correctly`() {
  // Given
  val testCases = listOf(
   Triple(900.0, 2000.0, 45.0),
   Triple(1400.0, 2000.0, 70.0),
   Triple(100.0, 1000.0, 10.0)
  )

  testCases.forEach { (value, total, expected) ->
   // When
   val result = useCase.calculatePercentage(value, total)

   // Then
   assertThat(result).isWithin(0.01).of(expected)
  }
 }


 @Test
 fun `should add meal to liked foods`() {
  // Given
  val meal = createMeal()

  // When
  useCase.like(meal)

  // Then
  assertThat(useCase.getLikedMeal()).contains(meal)
 }

 @Test
 fun `should add meal to disliked foods`() {
  // Given
  val meal = createMeal()

  // When
  useCase.dislike(meal)

  // Then
  assertThat(useCase.getDisLikedMeal()).contains(meal)
 }

}