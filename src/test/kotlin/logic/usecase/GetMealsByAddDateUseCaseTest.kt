package logic.usecase

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetMealsByAddDateUseCase
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlinx.datetime.LocalDate
import org.example.model.Meal
import org.example.model.Nutrition
import org.example.utils.MealAppException

class GetMealsByAddDateUseCaseTest {

    private lateinit var mealRepository: MealRepository
    private lateinit var getMealsByAddDate: GetMealsByAddDateUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        getMealsByAddDate = GetMealsByAddDateUseCase(mealRepository)
    }

    // Helper function to create Meal instances with defaults
    private fun createMeal(
        id: Int = 1,
        name: String = "Sample Meal",
        contributorId: Int = 123,
        description: String = "A tasty meal",
        ingredients: List<String> = listOf("ingredient1", "ingredient2"),
        numberOfIngredients: Int = 2,
        numberOfSteps: Int = 3,
        nutrition: Nutrition = Nutrition(100.0,100.0,100.0,100.0,100.0,100.0,100.0),
        preparationTime: Int = 30,
        steps: List<String> = listOf("Step 1", "Step 2", "Step 3"),
        submitted: LocalDate,
        tags: List<String> = listOf("tag1", "tag2")
    ): Meal {
        return Meal(
            id = id,
            name = name,
            contributorId = contributorId,
            description = description,
            ingredients = ingredients,
            numberOfIngredients = numberOfIngredients,
            numberOfSteps = numberOfSteps,
            nutrition = nutrition,
            preparationTime = preparationTime,
            steps = steps,
            submitted = submitted,
            tags = tags
        )
    }

    @Test
    fun `should return meals when meal exist in given date`(){
        val date = LocalDate(2024,2,2)
        val meals = listOf(
            createMeal(submitted = date),
            createMeal(id = 2, name = "second meal", submitted = date)
        )
        every { mealRepository.getMeals() } returns meals

        val result = getMealsByAddDate("2024-02-02")

        assertTrue(result.isSuccess)
        assertEquals(meals,result.getOrNull())
    }

    @Test
    fun `should fails with NoMealsFoundException when no meals exist for the given date`() {
        val meals = listOf(
            createMeal(submitted = LocalDate(2024, 5, 31))
        )
        every { mealRepository.getMeals() } returns meals

        val result = getMealsByAddDate.invoke("2024-06-01")

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is MealAppException.NoMealsFoundException)
    }

    @Test
    fun `should fails with InvalidDateFormatException when date format is invalid`() {
        val invalidDate = "06-01-2024"

        val result = getMealsByAddDate.invoke(invalidDate)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is MealAppException.InvalidDateFormatException)
        assertEquals("Invalid date format. Please use yyyy-MM-dd.", exception?.message)
    }
}