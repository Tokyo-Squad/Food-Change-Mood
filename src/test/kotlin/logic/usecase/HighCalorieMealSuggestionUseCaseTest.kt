package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import common.createNutrition
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.HighCalorieMealSuggestionUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HighCalorieMealSuggestionUseCaseTest {

    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: HighCalorieMealSuggestionUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        useCase = HighCalorieMealSuggestionUseCase(mealRepository)
    }

    @Test
    fun `should return high calorie meal when meals exist above threshold`() {
        val meals = listOf(
            createMeal(id = 1, nutrition = createNutrition(calories = 500.0)),
            createMeal(id = 2, nutrition = createNutrition(calories = 850.0)),
            createMeal(id = 3, nutrition = createNutrition(calories = 900.0))
        )
        every { mealRepository.getMeals() } returns meals

        val suggestion = useCase.getNextSuggestion()

        assertThat(suggestion).isNotNull()
        assertThat(suggestion!!.id).isIn(listOf(2, 3))
    }

    @Test
    fun `should return unique meals when getting multiple suggestions`() {
        val meals = listOf(
            createMeal(id = 1, nutrition = createNutrition(calories = 500.0)),
            createMeal(id = 2, nutrition = createNutrition(calories = 850.0)),
            createMeal(id = 3, nutrition = createNutrition(calories = 900.0))
        )
        every { mealRepository.getMeals() } returns meals

        val suggestedMealIds = mutableSetOf<Int>()
        repeat(3) {
            val suggestion = useCase.getNextSuggestion()
            suggestion?.let { suggestedMealIds.add(it.id) }
        }

        assertThat(suggestedMealIds).containsExactlyElementsIn(setOf(2, 3))
    }

    @Test
    fun `should return null when all high calorie meals have been suggested`() {
        val meals = listOf(
            createMeal(id = 1, nutrition = createNutrition(calories = 500.0)),
            createMeal(id = 2, nutrition = createNutrition(calories = 850.0)),
            createMeal(id = 3, nutrition = createNutrition(calories = 900.0))
        )
        every { mealRepository.getMeals() } returns meals

        repeat(2) { useCase.getNextSuggestion() }
        val suggestion = useCase.getNextSuggestion()

        assertThat(suggestion).isNull()
    }

    @Test
    fun `should return null when no high calorie meals exist`() {
        val meals = listOf(
            createMeal(id = 1, nutrition = createNutrition(calories = 500.0)),
            createMeal(id = 2, nutrition = createNutrition(calories = 600.0))
        )
        every { mealRepository.getMeals() } returns meals

        val suggestion = useCase.getNextSuggestion()

        assertThat(suggestion).isNull()
    }

    @Test
    fun `should call repository once when getting multiple suggestions`() {
        val meals = listOf(createMeal(id = 1, nutrition = createNutrition(calories = 850.0)))
        every { mealRepository.getMeals() } returns meals

        repeat(3) { useCase.getNextSuggestion() }

        verify(exactly = 1) { mealRepository.getMeals() }
    }
}