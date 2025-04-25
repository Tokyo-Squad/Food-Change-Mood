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
    fun `should invoke getMeals on MealRepository when executing GetSeafoodMealsSortedByProteinUseCase`() {
        // Given
        every { mealRepository.getMeals() } returns emptyList()

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        verify(exactly = 1) { mealRepository.getMeals() }
        assertThat(result).isEmpty()
    }

    @Test
    fun `should sort seafood meals by protein in descending order when description contains seafood`() {
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
        assertThat(result.map { it.second.id }).containsExactly(3, 1).inOrder()
    }

    @Test
    fun `should sort seafood meals by protein in descending order when tags contain seafood`() {
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
        assertThat(result.map { it.second.id }).containsExactly(3, 2).inOrder()
    }

    @Test
    fun `should sort seafood meals by protein when either description or tags contain seafood`() {
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
        assertThat(result.map { it.second.id }).containsExactly(2, 1).inOrder()
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
        assertThat(result).isEmpty()
    }

    @Test
    fun `should maintain order when protein content is equal`() {
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
    fun `should detect seafood meals regardless of case when present in description`(seafoodVariant: String) {
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
    fun `should detect seafood meals regardless of case when present in tags`(seafoodVariant: String) {
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
    fun `should not detect non-seafood terms when description variants similar to seafood are used`(nonSeafoodVariant: String) {
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
        assertThat(result[0].second.id).isEqualTo(2)
    }


    @Test
    fun `should return empty list when description is null and tags do not contain seafood`() {
        // Given
        every { mealRepository.getMeals() } returns listOf(
            createMeal(
                id = 1,
                description = null,
                tags = emptyList(),
                nutrition = createNutrition(protein = 10.0)
            )
        )

        // When
        val result = getSeafoodMealsSortedByProteinUseCase()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should assign sequential indices starting from 1 when meals are sorted`() {
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
        assertThat(result.map { it.first }).containsExactly(1, 2, 3).inOrder()
        assertThat(result.map { it.second.id }).containsExactly(5, 10, 15).inOrder()
    }
}