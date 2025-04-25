package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.ExploreCountriesFoodCultureUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExploreCountriesFoodCultureUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: ExploreCountriesFoodCultureUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        useCase = ExploreCountriesFoodCultureUseCase(mealRepository)
    }

    @Test
    fun `should return meals when country name matches meal name exactly`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Italian Pasta"),
            createMeal(id = 2, name = "Japanese Sushi")
        )

        val result = useCase("Italian")
        assertEquals(1, result.size)
    }

    @Test
    fun `should return meals when country name matches tag exactly`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Pasta", tags = listOf("Italian")),
            createMeal(id = 2, name = "Sushi", tags = listOf("Japanese"))
        )

        val result = useCase("Japanese")
        assertEquals(1, result.size)
    }

    @Test
    fun `should return meals when country name matches ingredient exactly`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Pasta", ingredients = listOf("Italian Basil")),
            createMeal(id = 2, name = "Sushi", ingredients = listOf("Japanese Rice")),
            createMeal(id = 3, name = "Burger", ingredients = listOf("American Cheese"))
        )

        val result = useCase("Italian")

        assertThat(result)
            .hasSize(1)
    }

    @Test
    fun `should return empty list when no matches found`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Pasta"),
            createMeal(id = 2, name = "Sushi")
        )

        val result = useCase("Mexican")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should find matches when country name has typos`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Italian Pasta"),
            createMeal(id = 2, name = "Japaneze Sushi") // Intentional typo
        )

        val result = useCase("Japaneze")
        assertEquals("Japaneze Sushi", result.first().name)
    }

    @Test
    fun `should limit results when there is 20 meals`() {
        every { mealRepository.getMeals() } returns List(30) {
            createMeal(id = 1, name = "Italian Dish $it")
        }

        val result = useCase("Italian")
        assertEquals(20, result.size)
    }

    @Test
    fun `should ignore case when matching country name`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "italian pasta")
        )

        val result = useCase("ITALIAN")
        assertEquals(1, result.size)
    }

    @Test
    fun `should match when there is partial country names`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Authentic iraqi Pasta"),
            createMeal(id = 2, ingredients = listOf("Japanese Style")),
            createMeal(id = 3, tags = listOf("Mexican Chili"))
        )

        val result = useCase("iraq")

        assertThat(result).hasSize(1)
    }

    @Test
    fun `should return empty list when there empty insert`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Authentic iraqi Pasta"),
            createMeal(id = 2, ingredients = listOf("Japanese Style")),
            createMeal(id = 3, tags = listOf("Mexican Chili"))
        )

        val result = useCase("")

        assertThat(result).isEmpty()
    }

    @Test
    fun `should not match when there is too short partials`() {
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, name = "Italian Pasta")
        )

        assertThat(useCase("it")).isEmpty()
    }
}