package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import common.createNutrition
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GymHelperUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GymHelperUseCaseTest {

    private val mealRepository: MealRepository = mockk()
    private lateinit var useCase: GymHelperUseCase

    @BeforeEach
    fun setUp() {
        useCase = GymHelperUseCase(mealRepository)
    }

    @Test
    fun `should return meals within default margins when invoked`() {
        // Given
        val meal1 = createMeal(
            id = 1,
            name = "Chicken Salad",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 450.0, protein = 30.0)
        )
        val meal2 = createMeal(
            id = 2,
            name = "Steak",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 600.0, protein = 40.0)
        )
        val meal3 = createMeal(
            id = 3,
            name = "Pasta",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 800.0, protein = 20.0)
        )
        every { mealRepository.getMeals() } returns listOf(meal1, meal2, meal3)

        // When
        val targetCalories = 500f
        val targetProtein = 35.0f
        val result = useCase.invoke(targetCalories, targetProtein)

        // Then
        assertThat(listOf(meal1)).isEqualTo(result)
    }

    @Test
    fun `should return meals within custom margins when invoked`() {
        // Given
        val meal1 = createMeal(
            id = 4,
            name = "Tofu Stir Fry",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 300.0, protein = 15.0)
        )
        val meal2 = createMeal(
            id = 5,
            name = "Beef Stew",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 320.0, protein = 25.0)
        )
        every { mealRepository.getMeals() } returns listOf(meal1, meal2)

        // When
        val targetCalories = 310f
        val targetProtein = 20f
        val customCalMargin = 20f
        val customProteinMargin = 6f
        val result = useCase.invoke(targetCalories, targetProtein, customCalMargin, customProteinMargin)


        assertThat(result).isEqualTo(listOf(meal1, meal2))
    }

    @Test
    fun `should return empty list when no meals match margins when invoked`() {
        // Given
        val meal1 = createMeal(
            id = 6,
            name = "Burger",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 900.0, protein = 50.0)
        )
        val meal2 = createMeal(
            id = 7,
            name = "Fries",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 500.0, protein = 5.0)
        )
        every { mealRepository.getMeals() } returns listOf(meal1, meal2)

        // When
        val targetCalories = 400f
        val targetProtein = 30f
        val result = useCase.invoke(targetCalories, targetProtein)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should include meals on the boundary of margins when invoked`() {
        // Given
        val meal1 = createMeal(
            id = 8,
            name = "Salmon",
            tags = emptyList(),
            description = null,
            nutrition = createNutrition(calories = 550.0, protein = 45.0)
        )
        every { mealRepository.getMeals() } returns listOf(meal1)

        // When
        val targetCalories = 500.0f
        val targetProtein = 35.0f
        val result = useCase.invoke(targetCalories, targetProtein)

        // Then
        assertThat(listOf(meal1)).isEqualTo(result)
    }
}