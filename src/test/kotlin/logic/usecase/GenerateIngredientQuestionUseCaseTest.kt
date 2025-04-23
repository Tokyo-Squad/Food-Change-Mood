package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GenerateIngredientQuestionUseCase
import org.example.model.QuestionWithWrongAnswers
import org.example.utils.MealAppException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class GenerateIngredientQuestionUseCaseTest {

    private lateinit var repository: MealRepository
    private lateinit var useCase: GenerateIngredientQuestionUseCase

    @BeforeTest
    fun setup() {
        repository = mockk()
    }

    @Test
    fun `should exclude correct meal's ingredients from wrong options when given a list of meals`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )

        val salad = createMeal(
            id = 2,
            name = "Salad",
            ingredients = listOf("Lettuce", "Cucumber", "Olive Oil")
        )

        every { repository.getMeals() } returns listOf(pizza, salad)
        useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val question: QuestionWithWrongAnswers = useCase()

        // Then
        val wrongOptions = question.options.filterNot { it == question.correctAnswer }
        assertThat(wrongOptions).containsNoneIn(question.meal.ingredients)
    }

    @Test
    fun `should include the correct answer among the options when given meals with one empty ingredients list`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )
        val salad = createMeal(
            id = 2,
            name = "Salad",
            ingredients = listOf("Lettuce", "Cucumber", "Olive Oil")
        )
        val emptyMeal = createMeal(
            id = 3,
            name = "Empty Meal",
            ingredients = emptyList()
        )
        every { repository.getMeals() } returns listOf(pizza, salad, emptyMeal)
        val useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val question = useCase()

        // Then
        assertThat(question.options).contains(question.correctAnswer)
    }


    @Test
    fun `should generate a question containing the correct answer when repository returns meals`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )

        val salad = createMeal(
            id = 2,
            name = "Salad",
            ingredients = listOf("Lettuce", "Cucumber", "Olive Oil")
        )

        every { repository.getMeals() } returns listOf(pizza, salad)
        useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val question: QuestionWithWrongAnswers = useCase()

        // Then
        assertThat(question.options).contains(question.correctAnswer)
    }

    @Test
    fun `should return exactly three options when given meals with one empty ingredients list`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )
        val salad = createMeal(
            id = 2,
            name = "Salad",
            ingredients = listOf("Lettuce", "Cucumber", "Olive Oil")
        )
        val emptyMeal = createMeal(
            id = 3,
            name = "Empty Meal",
            ingredients = emptyList()
        )
        every { repository.getMeals() } returns listOf(pizza, salad, emptyMeal)
        val useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val question = useCase()

        // Then
        assertThat(question.options).hasSize(3)
    }

    @Test
    fun `should generate a question containing the correct answer when given a list of meals`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )

        val salad = createMeal(
            id = 2,
            name = "Salad",
            ingredients = listOf("Lettuce", "Cucumber", "Olive Oil")
        )

        every { repository.getMeals() } returns listOf(pizza, salad)
        useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val question: QuestionWithWrongAnswers = useCase()

        // Then
        assertThat(question.options).contains(question.correctAnswer)
    }

    @Test
    fun `should exclude shared ingredients like Cheese from wrong options when meals share ingredients`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )

        val pasta = createMeal(
            id = 2,
            name = "Pasta",
            ingredients = listOf("Cheese", "Tomato Sauce", "Spaghetti")
        )

        val salad = createMeal(
            id = 3,
            name = "Salad",
            ingredients = listOf("Lettuce", "Cucumber", "Olive Oil")
        )

        every { repository.getMeals() } returns listOf(pizza, pasta, salad)
        val useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val question: QuestionWithWrongAnswers = useCase()
        val wrongOptions = question.options.filterNot { it == question.correctAnswer }

        // Then
        assertThat(wrongOptions).doesNotContain("Cheese")
    }

    @Test
    fun `should throw InsufficientWrongAnswersException when there are not enough wrong ingredients`() {
        // Given
        val onlyOneMeal = createMeal(
            id = 1,
            ingredients = listOf("Ingredient1", "Ingredient2")
        )

        every { repository.getMeals() } returns listOf(onlyOneMeal)
        useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val exception = assertFailsWith<MealAppException.InsufficientWrongAnswersException> {
            useCase()
        }
        // Then
        assertThat(exception.message).isEqualTo("Not enough wrong ingredient options.")
    }

    @Test
    fun `should throw InsufficientWrongAnswersException when there are not enough unique wrong ingredients`() {
        // Given
        val meal1 = createMeal(
            id = 1,
            ingredients = listOf("Cheese", "Tomato", "Dough")
        )

        val meal2 = createMeal(
            id = 2,
            ingredients = listOf("Cheese", "Tomato") // Shares all ingredients with meal1
        )

        every { repository.getMeals() } returns listOf(meal1, meal2)
        useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val exception = assertFailsWith<MealAppException.InsufficientWrongAnswersException> {
            useCase()
        }
        // Then
        assertThat(exception.message).isEqualTo("Not enough wrong ingredient options.")
    }

    @Test
    fun `should throw NoMealsAvailableException when repository fails and returns an empty list`() {
        // Given
        every { repository.getMeals() } returns emptyList()

        useCase = GenerateIngredientQuestionUseCase(repository)

        // When
        val exception = assertFailsWith<MealAppException.NoMealsAvailableException> {
            useCase()
        }
        // Then
        assertThat(exception.message).isEqualTo("No meals available in the repository.")
    }

    @Test
    fun `should throw InsufficientWrongAnswersException when not enough wrong ingredients are available`() {
        // Given
        val pizza = createMeal(
            id = 1,
            name = "Pizza",
            ingredients = listOf("Cheese", "Tomato Sauce", "Dough")
        )

        val pasta = createMeal(
            id = 2,
            name = "Pasta",
            ingredients = listOf("Cheese", "Tomato Sauce", "Spaghetti")
        )
        // When
        every { repository.getMeals() } returns listOf(pizza, pasta)
        useCase = GenerateIngredientQuestionUseCase(repository)

        // Then
        val exception = assertFailsWith<MealAppException.InsufficientWrongAnswersException> {
            useCase()
        }
        assertThat(exception).hasMessageThat().isEqualTo("Not enough wrong ingredient options.")
    }
}