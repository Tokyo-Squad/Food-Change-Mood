package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetMealsByNameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetMealsByNameUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: GetMealsByNameUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        useCase = GetMealsByNameUseCase(mealRepository)
    }

    @Test
    fun `should return exact name matches`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Cheeseburger"),
            createMeal(2, name = "Salad")
        )

        val result = useCase("Cheeseburger")

        assertEquals(1, result.size)
    }

    @Test
    fun `should return fuzzy matches when enter has typos`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Cheeseburger")
        )

        val result = useCase("Cheesburgr")

        assertEquals("Cheeseburger", result.first().name)
    }

    @Test
    fun `should order results by relevance score`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Burger King"),
            createMeal(2, name = "Cheeseburger"),
            createMeal(3, name = "Veggie Burger")
        )

        val result = useCase("Burger")

        assertEquals("Burger King", result.first().name)
    }

    @Test
    fun `should ignore case differences`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Cheeseburger")
        )

        val result = useCase("CHEESE")

        assertEquals(1, result.size)
    }

    @Test
    fun `should handle special characters in query`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Café Latte")
        )

        val result = useCase("cafe")

        assertEquals("Café Latte", result.first().name)
    }

    @Test
    fun `should return empty list for no matches`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Pizza")
        )

        val result = useCase("Burger")

        assertThat(result).isEmpty()
    }

    @Test
    fun `should limit results to maxResults parameter`() {
        every { mealRepository.getMeals() } returns List(30) {
            createMeal(1, name = "Burger $it")
        }

        val result = useCase("Burger")

        assertEquals(20, result.size)
    }

    @Test
    fun `should return all meals for very short queries`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Cheese"),
            createMeal(2, name = "Chicken")
        )

        val result = useCase("ch")

        assertEquals(2, result.size)
    }

    @Test
    fun `should handle empty repository`() {
        every { mealRepository.getMeals() } returns emptyList()

        val result = useCase("Burger")

        assertThat(result).isEmpty()
    }

    @Test
    fun `should match partial words`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(1, name = "Cheeseburger")
        )

        val result = useCase("cheese")

        assertEquals(1, result.size)
    }

    @Test
    fun `should handle empty query by returning empty list`() {
        val mockMeals = listOf(
            createMeal(id = 1, name = "Cheeseburger"),
            createMeal(id = 2, name = "Pizza")
        )
        every { mealRepository.getMeals() } returns mockMeals

        val result = useCase("")

        assertThat(result.isEmpty())
    }
}