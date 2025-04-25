package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetIraqiMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetIraqiMealsUseCaseTest {

    private val mealRepository: MealRepository = mockk()
    private lateinit var useCase: GetIraqiMealsUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetIraqiMealsUseCase(mealRepository)
    }

    @Test
    fun `should return only meals with iraqi tag when invoked`() {
        // Given
        val iraqiTaggedMeal = createMeal(id = 1, name = "Kebab", tags = listOf("Iraqi"), description = "Delicious")
        val otherMeal = createMeal(id = 2, name = "Pasta", tags = listOf("Italian"), description = null)
        every { mealRepository.getMeals() } returns listOf(iraqiTaggedMeal, otherMeal)

        // When
        val result = useCase.invoke()

        // Then
        assertThat(listOf(iraqiTaggedMeal)).isEqualTo(result)
    }

    @Test
    fun `should return only meals with description iraq when invoked`() {
        // Given
        val describedMeal = createMeal(id = 3, name = "Dolma", tags = listOf("Turkish"), description = "Iraq")
        val otherMeal = createMeal(id = 4, name = "Sushi", tags = listOf("Japanese"), description = null)
        every { mealRepository.getMeals() } returns listOf(describedMeal, otherMeal)

        // When
        val result = useCase.invoke()

        // Then
        assertThat(listOf(describedMeal)).isEqualTo(result)
    }

    @Test
    fun `should be case insensitive when matching tags and descriptions when invoked`() {
        // Given
        val mixedCaseTagMeal = createMeal(id = 5, name = "Tashreeb", tags = listOf("IrAqI"), description = null)
        val mixedCaseDescMeal = createMeal(id = 6, name = "Stew", tags = listOf("MiddleEast"), description = "iRaQ")
        val otherMeal = createMeal(id = 7, name = "Burger", tags = listOf("American"), description = "Fast food")
        every { mealRepository.getMeals() } returns listOf(mixedCaseTagMeal, mixedCaseDescMeal, otherMeal)

        // When
        val result = useCase.invoke()

        // Then
        assertThat(listOf(mixedCaseTagMeal, mixedCaseDescMeal)).isEqualTo(result)
    }

    @Test
    fun `should return empty list when no meals match criteria when invoked`() {
        // Given
        val nonIraqi1 = createMeal(id = 8, name = "Curry", tags = listOf("Indian"), description = "Spicy")
        val nonIraqi2 = createMeal(id = 9, name = "Taco", tags = listOf("Mexican"), description = "Corn")
        every { mealRepository.getMeals() } returns listOf(nonIraqi1, nonIraqi2)

        // When
        val result = useCase.invoke()

        // Then
        assertThat(result).isEmpty()
    }
}