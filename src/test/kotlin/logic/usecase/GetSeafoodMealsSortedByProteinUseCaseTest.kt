package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import common.createNutrition
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetSeafoodMealsSortedByProteinUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

class GetSeafoodMealsSortedByProteinUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var getSeafoodMealsSortedByProteinUseCase: GetSeafoodMealsSortedByProteinUseCase

    @BeforeEach
    fun setup() {
        mealRepository = mockk()
        getSeafoodMealsSortedByProteinUseCase = GetSeafoodMealsSortedByProteinUseCase(mealRepository)
    }

    @Test
    fun `should return seafood meals from description sorted by protein in descending order`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = "A delicious seafood pasta",
                nutrition = createNutrition(protein = 15.0)
            ),
            createMeal(
                id = 2,
                description = "Grilled chicken with vegetables",
                nutrition = createNutrition(protein = 25.0)
            ),
            createMeal(
                id = 3,
                description = "Seafood paella with rice",
                nutrition = createNutrition(protein = 20.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(2)
        assertThat(result[0].first).isEqualTo(1)
        assertThat(result[0].second.id).isEqualTo(3)
        assertThat(result[1].first).isEqualTo(2)
        assertThat(result[1].second.id).isEqualTo(1)
    }

    @Test
    fun `should return seafood meals from tags sorted by protein in descending order`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                tags = listOf("italian", "pasta"),
                nutrition = createNutrition(protein = 10.0)
            ),
            createMeal(
                id = 2,
                tags = listOf("seafood", "dinner"),
                nutrition = createNutrition(protein = 18.0)
            ),
            createMeal(
                id = 3,
                tags = listOf("seafood", "lunch", "healthy"),
                nutrition = createNutrition(protein = 22.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(2)
        assertThat(result[0].first).isEqualTo(1)
        assertThat(result[0].second.id).isEqualTo(3)
        assertThat(result[1].first).isEqualTo(2)
        assertThat(result[1].second.id).isEqualTo(2)
    }

    @Test
    fun `should return seafood meals from both description and tags sorted by protein`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = "A delicious seafood pasta",
                nutrition = createNutrition(protein = 15.0)
            ),
            createMeal(
                id = 2,
                tags = listOf("seafood", "dinner"),
                nutrition = createNutrition(protein = 18.0)
            ),
            createMeal(
                id = 3,
                description = "Grilled chicken with vegetables",
                tags = listOf("chicken", "healthy"),
                nutrition = createNutrition(protein = 25.0)
            )
        )


        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(2)
        assertThat(result[0].first).isEqualTo(1)
        assertThat(result[0].second.id).isEqualTo(2)
        assertThat(result[1].first).isEqualTo(2)
        assertThat(result[1].second.id).isEqualTo(1)
    }

    @Test
    fun `should return empty list when no seafood meals exist`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = "Grilled chicken",
                tags = listOf("chicken", "dinner"),
                nutrition = createNutrition(protein = 20.0)
            ),
            createMeal(
                id = 2,
                description = "Vegetable curry",
                tags = listOf("vegetarian", "spicy"),
                nutrition = createNutrition(protein = 8.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).isEmpty()
    }

    @Test
    fun `should handle empty repository data gracefully`() {
        // Given
        every { mealRepository.getMeals() } returns emptyList()

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).isEmpty()
    }

    @Test
    fun `should handle meals with equal protein content by maintaining order`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = "Seafood pasta",
                nutrition = createNutrition(protein = 18.0)
            ),
            createMeal(
                id = 2,
                tags = listOf("seafood"),
                nutrition = createNutrition(protein = 18.0)
            ),
            createMeal(
                id = 3,
                description = "Another seafood dish",
                nutrition = createNutrition(protein = 18.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(3)
        assertThat(result.map { it.second.id }).containsExactly(1, 2, 3).inOrder()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "seafood",
            "SEAFOOD",
            "Seafood",
            "SeAfOoD",
            "seafood meal",
            "delicious seafood",
            "fresh SEAFOOD",
            "Mixed seafood platter"
        ]
    )
    fun `should detect seafood meals regardless of case in description`(seafoodVariant: String) {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = seafoodVariant,
                nutrition = createNutrition(protein = 20.0)
            ),
            createMeal(
                id = 2,
                description = "Chicken curry",
                nutrition = createNutrition(protein = 15.0)
            )
        )


        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(1)
        assertThat(result[0].second.id).isEqualTo(1)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "seafood",
            "SEAFOOD",
            "Seafood",
            "SeAfOoD",
            "seafood-meal",
            "fresh-seafood"
        ]
    )
    fun `should detect seafood meals regardless of case in tags`(seafoodVariant: String) {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                tags = listOf(seafoodVariant, "dinner"),
                nutrition = createNutrition(protein = 20.0)
            ),
            createMeal(
                id = 2,
                tags = listOf("chicken", "curry"),
                nutrition = createNutrition(protein = 15.0)
            )
        )
        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(1)
        assertThat(result[0].second.id).isEqualTo(1)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "fish",
            "see food",
            "see-food",
            "seafo od",
            "sea_food",
            "sea.food",
            "seefood"
        ]
    )
    fun `should not detect terms that are not actually seafood`(nonSeafoodVariant: String) {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = nonSeafoodVariant,
                nutrition = createNutrition(protein = 20.0)
            ),
            createMeal(
                id = 2,
                description = "Actual seafood dish",
                nutrition = createNutrition(protein = 15.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(1)
        assertThat(result[0].second.id).isEqualTo(2)
    }

    @Test
    fun `should correctly assign sequential indices starting from 1`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 5,
                description = "Seafood curry",
                nutrition = createNutrition(protein = 25.0)
            ),
            createMeal(
                id = 10,
                tags = listOf("seafood"),
                nutrition = createNutrition(protein = 20.0)
            ),
            createMeal(
                id = 15,
                description = "Another seafood dish",
                nutrition = createNutrition(protein = 15.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).hasSize(3)
        assertThat(result[0].first).isEqualTo(1)
        assertThat(result[1].first).isEqualTo(2)
        assertThat(result[2].first).isEqualTo(3)
        assertThat(result.map { it.second.id }).containsExactly(5, 10, 15).inOrder()
    }
}