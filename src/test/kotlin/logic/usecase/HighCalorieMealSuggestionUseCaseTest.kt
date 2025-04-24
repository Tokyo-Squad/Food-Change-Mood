package logic.usecase

import com.google.common.truth.Truth.assertThat

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDate
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.HighCalorieMealSuggestionUseCase
import org.example.model.Meal
import org.example.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
class HighCalorieMealSuggestionUseCaseTest {

    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: HighCalorieMealSuggestionUseCase

    private fun createMeal(
        id: Int = 1,
        name: String = "Sample Meal",
        calories: Double = 500.0,
        submitted: LocalDate = LocalDate(2024, 1, 1)
    ): Meal {
        return Meal(
            id = id,
            name = name,
            preparationTime = 30,
            contributorId = 101,
            submitted = submitted,
            tags = listOf("tag1", "tag2"),
            nutrition = Nutrition(
                calories = calories,
                totalFat = 20.0,
                sugar = 5.0,
                sodium = 500.0,
                protein = 25.0,
                saturatedFat = 8.0,
                carbohydrates = 40.0
            ),
            numberOfSteps = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "Sample description",
            ingredients = listOf("ingredient1", "ingredient2"),
            numberOfIngredients = 2
        )
    }

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        useCase = HighCalorieMealSuggestionUseCase(mealRepository)
    }

    @Test
    fun `should return high calorie meal when meals exist above threshold`() {
        val meals = listOf(
            createMeal(id = 1, calories = 500.0),
            createMeal(id = 2, calories = 850.0),
            createMeal(id = 3, calories = 900.0)
        )
        every { mealRepository.getMeals() } returns meals

        val suggestion = useCase.getNextSuggestion()

        assertThat(suggestion).isNotNull()
        assertThat(suggestion!!.nutrition.calories).isGreaterThan(700.0)
        assertThat(suggestion.id).isIn(listOf(2, 3))
    }

    @Test
    fun `should return unique meals when getting multiple suggestions`() {
        val meals = listOf(
            createMeal(id = 1, calories = 500.0),
            createMeal(id = 2, calories = 850.0),
            createMeal(id = 3, calories = 900.0)
        )
        every { mealRepository.getMeals() } returns meals

        val suggestedMealIds = mutableSetOf<Int>()
        repeat(3) {
            val suggestion = useCase.getNextSuggestion()
            suggestion?.let { suggestedMealIds.add(it.id) }
        }

        assertThat(suggestedMealIds).hasSize(2)
        assertThat(suggestedMealIds).containsExactlyElementsIn(setOf(2, 3))
    }

    @Test
    fun `should return null when all high calorie meals have been suggested`() {
        val meals = listOf(
            createMeal(id = 1, calories = 500.0),
            createMeal(id = 2, calories = 850.0),
            createMeal(id = 3, calories = 900.0)
        )
        every { mealRepository.getMeals() } returns meals

        repeat(2) { useCase.getNextSuggestion() }
        val suggestion = useCase.getNextSuggestion()

        assertThat(suggestion).isNull()
    }

    @Test
    fun `should return null when no high calorie meals exist`() {
        val meals = listOf(
            createMeal(id = 1, calories = 500.0),
            createMeal(id = 2, calories = 600.0)
        )
        every { mealRepository.getMeals() } returns meals

        val suggestion = useCase.getNextSuggestion()

        assertThat(suggestion).isNull()
    }

    @Test
    fun `should call repository once when getting multiple suggestions`() {
        val meals = listOf(createMeal(id = 1, calories = 850.0))
        every { mealRepository.getMeals() } returns meals

        repeat(3) { useCase.getNextSuggestion() }

        verify(exactly = 1) { mealRepository.getMeals() }
    }
}