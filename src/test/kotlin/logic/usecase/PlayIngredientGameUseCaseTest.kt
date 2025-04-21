package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import io.mockk.every
import io.mockk.mockk
import org.example.logic.usecase.GenerateIngredientQuestionUseCase
import org.example.logic.usecase.PlayIngredientGameUseCase
import org.example.model.QuestionWithWrongAnswers
import org.example.utils.MealAppException
import kotlin.test.BeforeTest
import kotlin.test.Test

class PlayIngredientGameUseCaseTest {

 private lateinit var generateQuestionUseCase: GenerateIngredientQuestionUseCase
 private lateinit var useCase: PlayIngredientGameUseCase

 @BeforeTest
 fun setup() {
  generateQuestionUseCase = mockk()
  useCase = PlayIngredientGameUseCase(generateQuestionUseCase)
 }

 @Test
 fun `should return full score and success message when user answers all questions correctly`() {
  // Given
  val pizza = createMeal(
   id = 1,
   name = "Pizza",
   ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
  )

  every { generateQuestionUseCase() } returnsMany List(15) {
   QuestionWithWrongAnswers(
    meal = pizza,
    correctAnswer = "Cheese",
    options = listOf("Cheese", "Cucumber", "Lettuce")
   )
  }

  // When
  val result = useCase(promptUser = { _, _ -> "Cheese" })

  // Then
  assertThat(result.finalScore).isEqualTo(15 * 1000)
  assertThat(result.correctAnswers).isEqualTo(15)
  assertThat(result.message).isEqualTo("The max correct answers reached.")
 }
 @Test
 fun `should update score after answering correctly when user answers correctly on the first try`() {
  // Given
  val pizza = createMeal(
   id = 1,
   name = "Pizza",
   ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
  )

  every { generateQuestionUseCase() } returnsMany List(5) {
   QuestionWithWrongAnswers(
    meal = pizza,
    correctAnswer = "Cheese",
    options = listOf("Cheese", "Lettuce", "Cucumber")
   )
  }

  // When
  var callCount = 0
  val result = useCase(promptUser = { _, _ ->
   callCount++
   if (callCount == 1) "Cheese" else "Wrong"
  })

  // Then
  assertThat(result.correctAnswers).isEqualTo(1)
  assertThat(result.finalScore).isEqualTo(1000)
 }

 @Test
 fun `should stop game and return partial score when user answers incorrectly`() {
  // Given
  val pizza = createMeal(
   id = 1,
   name = "Pizza",
   ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
  )

  every { generateQuestionUseCase() } returnsMany List(5) {
   QuestionWithWrongAnswers(
    meal = pizza,
    correctAnswer = "Cheese",
    options = listOf("Cheese", "Lettuce", "Cucumber")
   )
  }

  // When
  var callCount = 0
  val result = useCase(promptUser = { _, _ ->
   callCount++
   if (callCount == 2) "Wrong" else "Cheese"
  })

  // Then
  assertThat(result.correctAnswers).isLessThan(15)
  assertThat(result.message).startsWith("Wrong answer. Correct was")
 }

 @Test
 fun `should handle insufficient wrong answer exception by retrying until max retries reached when generateQuestionUseCase throws an exception`() {
  // Given
  val generateQuestionUseCase = mockk<GenerateIngredientQuestionUseCase>()
  every {
   generateQuestionUseCase()
  } throws MealAppException.InsufficientWrongAnswersException("Not enough wrong ingredient options.")

  val useCase = PlayIngredientGameUseCase(generateQuestionUseCase)

  // When
  val result = useCase(promptUser = { _, _ -> null })

  // Then
  assertThat(result.message).isEqualTo("Game stopped: Too many failed attempts to generate a valid question.")
  assertThat(result.correctAnswers).isEqualTo(0)
  assertThat(result.finalScore).isEqualTo(0)
 }

 @Test
 fun `should stop immediately and return message when no meals are available`() {
  // Given
  every { generateQuestionUseCase() } throws MealAppException.NoMealsAvailableException("No meals available in the repository.")

  // When
  val result = useCase(promptUser = { _, _ -> null })

  // Then
  assertThat(result.message).isEqualTo("Game stopped: No meals available in the repository.")
  assertThat(result.correctAnswers).isEqualTo(0)
  assertThat(result.finalScore).isEqualTo(0)
 }

}