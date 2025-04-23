package logic.usecase

import com.google.common.truth.Truth.assertThat
import common.createMeal
import common.listOfMoreThenTenMeals
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.EasyFoodSuggestionUseCase
import org.example.model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class EasyFoodSuggestionUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: EasyFoodSuggestionUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        useCase = EasyFoodSuggestionUseCase(mealRepository)
    }

    @Test
    fun `should return easy food meals when when meals they are available`() {
        //Given
        val listOfMeals = listOf(
            createMeal(
                id = 1,
                name = "Healthy Burger",
                preparationTime = 10,
                numberOfIngredients = 3,
                numberOfSteps = 3,
            ), createMeal(
                id = 2,
                name = "Unhealthy Burger",
                preparationTime = 12,
                numberOfIngredients = 3,
                numberOfSteps = 3,
            ), createMeal(
                id = 3,
                name = "Healthy Salad",
                preparationTime = 10,
                numberOfIngredients = 8,
                numberOfSteps = 9,
            )
        )
        every { mealRepository.getMeals() } returns listOfMeals

        //When
        val result = useCase.getEasyMeals()

        //Then
        assertThat(result).hasSize(2)
    }

    @Test
    fun `should return empty list when when easy meals not available`() {
        //Given
        val listOfMeals = emptyList<Meal>()
        every { mealRepository.getMeals() } returns listOfMeals

        //Then
        val result = useCase.getEasyMeals()

        //When
        assertThat(result).isEmpty()

    }

    @Test
    fun `should return empty list when when at least one conditions of easy meals in list invalid`() {
        //Given
        val listOfMeals = listOf(
            createMeal(
                id = 1,
                name = "Healthy Burger",
                preparationTime = 40,
                numberOfIngredients = 3,
                numberOfSteps = 3,
            ), createMeal(
                id = 2,
                name = "Unhealthy Burger",
                preparationTime = 10,
                numberOfIngredients = 3,
                numberOfSteps = 8,
            ), createMeal(
                id = 3,
                name = "Healthy Salad",
                preparationTime = 20,
                numberOfIngredients = 9,
                numberOfSteps = 5,
            )
        )
        every { mealRepository.getMeals() } returns listOfMeals

        //When
        val result = useCase.getEasyMeals()

        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `should include meals with exactly 30 minutes preparation time, 5 ingredients, and 6 steps`() {
        //Given
        val boundaryMeal = createMeal(
            id = 1,
            name = "Boundary Meal",
            preparationTime = 30,
            numberOfIngredients = 5,
            numberOfSteps = 6,
        )
        every { mealRepository.getMeals() } returns listOf(boundaryMeal)

        //When
        val result = useCase.getEasyMeals()

        //Then
        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Boundary Meal")
    }

    @Test
    fun `should exclude meals with preparation time 31 minutes, 6 ingredients, or 7 steps`() {
        //Given
        val listOfMeals = listOf(
            createMeal(
                id = 1,
                name = "31 minutes",
                preparationTime = 31,
                numberOfIngredients = 5,
                numberOfSteps = 6,
            ),
            createMeal(
                id = 2,
                name = "6 ingredients",
                preparationTime = 30,
                numberOfIngredients = 6,
                numberOfSteps = 6,
            ),
            createMeal(
                id = 3,
                name = "7 steps",
                preparationTime = 30,
                numberOfIngredients = 5,
                numberOfSteps = 7,
            )
        )
        every { mealRepository.getMeals() } returns listOfMeals

        //When
        val result = useCase.getEasyMeals()

        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return 10 easy food meals when when easy meals are more then 10`() {
        //Given
        val listOfMeals = listOfMoreThenTenMeals()
        every { mealRepository.getMeals() } returns listOfMeals

        //When
        val result = useCase.getEasyMeals()

        //Then
        assertThat(result).hasSize(10)
    }
}