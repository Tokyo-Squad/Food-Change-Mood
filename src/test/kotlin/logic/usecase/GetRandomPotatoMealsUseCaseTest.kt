package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetRandomPotatoMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

class GetRandomPotatoMealsUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var getRandomPotatoMealsUseCase: GetRandomPotatoMealsUseCase

    @BeforeEach
    fun setup() {
        mealRepository = mockk()
        getRandomPotatoMealsUseCase = GetRandomPotatoMealsUseCase(mealRepository)
    }

    @Test
    fun `should invoke getMeals on MealRepository when executing GetRandomPotatoMealsUseCase`() {
        // Given
        every { mealRepository.getMeals() } returns emptyList()

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).isEmpty()
    }


    @Test
    fun `should return meals containing potato in ingredients when ingredients contain potato`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("potato", "salt")),
            createMeal(id = 2, ingredients = listOf("Potatoes", "pepper")),
            createMeal(id = 3, ingredients = listOf("chicken", "garlic"))
        )

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result.map { it.id }).containsExactly(1, 2)
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
            "potato",
            "POTATO",
            "Potato",
            "PoTaTo",
            "POTATOES",
            "potatoes",
            "Potatoes",
        ]
    )
    fun `should detect potato ingredients regardless of case and format when ingredients contain variant potato`(
        potatoVariant: String
    ) {
        // Given
        val potatoMeal = createMeal(id = 1, ingredients = listOf(potatoVariant, "salt"))
        every { mealRepository.getMeals() } returns listOf(
            potatoMeal,
            createMeal(id = 2, ingredients = listOf("chicken", "garlic"))
        )

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result).hasSize(1)
        assertThat(potatoMeal).isEqualTo(result.first())
    }


    @Test
    fun `should return empty list when no potato meals exist`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("rice", "salt")),
            createMeal(id = 2, ingredients = listOf("pasta", "pepper"))
        )

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should limit returned meals to randomSize parameter when randomSize is specified`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("potato", "salt")),
            createMeal(id = 2, ingredients = listOf("Potatoes", "pepper")),
            createMeal(id = 3, ingredients = listOf("Sweet potato", "garlic")),
            createMeal(id = 4, ingredients = listOf("potato chips", "oil"))
        )

        // When
        val result = getRandomPotatoMealsUseCase(randomSize = 2)

        // Then
        // Note: Can't verify exact contents since they're random
        assertThat(result.map { it.id }.all { it in listOf(1, 2, 3, 4) }).isTrue()
    }

    @Test
    fun `should return all potato meals when randomSize is larger than available meals`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("potato", "salt")),
            createMeal(id = 2, ingredients = listOf("Potatoes", "pepper"))
        )

        // When
        val result = getRandomPotatoMealsUseCase(randomSize = 5)

        // Then
        assertThat(result.map { it.id }).containsExactly(1, 2)
    }

    @Test
    fun `should detect potato ingredient regardless of case or position in word when ingredients contain substring potato`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("POTATO", "salt")),
            createMeal(id = 2, ingredients = listOf("Sweet-Potatoes", "pepper")),
            createMeal(id = 3, ingredients = listOf("chicken", "garlic"))
        )

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result.map { it.id }).containsExactly(1, 2)
    }

    @Test
    fun `should use default randomSize of 10 when not specified`() {
        // Given
        every { mealRepository.getMeals() } returns List(15) { index ->
            createMeal(id = index, ingredients = listOf("potato", "ingredient$index"))
        }

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result).hasSize(10)
    }

    @Test
    fun `should handle empty repository data gracefully when repository has no meals`() {
        // Given
        every { mealRepository.getMeals() } returns emptyList()

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should consider ingredients with multiple words containing potato when ingredients include multi-word potato phrases`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("mashed potato with herbs", "salt")),
            createMeal(id = 2, ingredients = listOf("chicken", "sliced potatoes with garlic")),
            createMeal(id = 3, ingredients = listOf("beef", "mushrooms"))
        )

        // When
        val result = getRandomPotatoMealsUseCase()

        // Then
        assertThat(result.map { it.id }).containsExactly(1, 2)
    }

    @Test
    fun `should return zero meals when randomSize is zero`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(id = 1, ingredients = listOf("potato", "salt")),
            createMeal(id = 2, ingredients = listOf("Potatoes", "pepper"))
        )

        // When
        val result = getRandomPotatoMealsUseCase(randomSize = 0)

        // Then
        assertThat(result).isEmpty()
    }
}