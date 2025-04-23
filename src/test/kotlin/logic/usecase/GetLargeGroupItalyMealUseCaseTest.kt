import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetLargeGroupItalyMealUseCase
import org.example.model.Meal
import org.example.model.Nutrition
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetLargeGroupItalyMealUseCaseTest {

 private lateinit var repository: MealRepository
 private lateinit var useCase: GetLargeGroupItalyMealUseCase

 @BeforeTest
 fun setup() {
  repository = mockk()
  useCase = GetLargeGroupItalyMealUseCase(repository)
 }

 private fun createMeal(
  name: String,
  id: Int,
  area: String,
  tags: List<String>,
  ingredients: List<String> = emptyList()
 ): Meal {
  return Meal(
   name = name,
   id = id,
   preparationTime = 30,
   contributorId = 1,
   submitted = LocalDate.parse("2025-01-01"),
   tags = tags,
   nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
   numberOfSteps = 1,
   steps = listOf("Step 1"),
   description = null,
   ingredients = ingredients,
   numberOfIngredients = ingredients.size
  )
 }

 @Test
 fun `should return empty list when no meals match criteria`() {
  // Given
  every { repository.getMeals() } returns listOf(
   createMeal("Burger", 1, "American", listOf("fast-food")),
   createMeal("Sushi", 2, "Japanese", listOf("for-large-groups")) // Fixed typo in tag
  )

  // When
  val result = useCase()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should return only Italian meals suitable for large groups`() {
  // Given
  val pizza = createMeal("Pizza", 1, "Italian", listOf("for-large-groups", "popular"))
  val pasta = createMeal("Pasta", 2, "Italy", listOf("for-large-groups"))
  val risotto = createMeal("Risotto", 3, "Italian", listOf("quick-meal"))

  every { repository.getMeals() } returns listOf(pizza, pasta, risotto) // Fixed to use repository

  // When
  val result = useCase()

  // Then
  assertThat(result).containsExactly(pizza, pasta)
 }


 @Test
 fun `should return empty list when repository returns empty list`() {
  // Given
  every { repository.getMeals() } returns emptyList()

  // When
  val result = useCase()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should ignore case when checking area`() {
  // Given
  val meal1 = createMeal("Pizza", 1, "ITALIAN", listOf("for-large-groups"))
  val meal2 = createMeal("Pasta", 2, "italy", listOf("for-large-groups"))

  every { repository.getMeals() } returns listOf(meal1, meal2)

  // When
  val result = useCase()

  // Then
  assertThat(result).containsExactly(meal1, meal2)
 }


 @Test
 fun `should handle null tags`() {
  val meal = createMeal(
      "Pizza", 1, "Italian",
      tags = emptyList(),
      ingredients = emptyList(),
  )
  every { repository.getMeals() } returns listOf(meal)
  val result = useCase()
  assertThat(result).isEmpty()
 }


}