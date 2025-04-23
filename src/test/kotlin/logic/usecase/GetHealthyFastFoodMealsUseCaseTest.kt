package logic.usecase


import com.google.common.truth.Truth.assertThat
import common.createMeal
import common.createNutrition
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecase.GetHealthyFastFoodMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetHealthyFastFoodMealsUseCaseTest {

    private lateinit var repository: MealRepository
    private lateinit var useCase: GetHealthyFastFoodMealsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetHealthyFastFoodMealsUseCase(repository)
    }

    @Test
    fun `should return only healthy fast food meals with appropriate nutrition and preparation time when meals are available`() {
        // Given
        val meal1 = createMeal(
            id = 1,
            name = "Healthy Burger",
            preparationTime = 10,
            nutrition = createNutrition(
                totalFat = 15.0,
                saturatedFat = 5.0,
                carbohydrates = 20.0
            )
        )
        val meal2 = createMeal(
            id = 2,
            name = "Unhealthy Burger",
            preparationTime = 12,
            nutrition = createNutrition(
                totalFat = 30.0,
                saturatedFat = 10.0,
                carbohydrates = 40.0
            )
        )
        val meal3 = createMeal(
            id = 3,
            name = "Healthy Salad",
            preparationTime = 10,
            nutrition = createNutrition(
                totalFat = 5.0,
                saturatedFat = 1.0,
                carbohydrates = 10.0
            )
        )

        every { repository.getMeals() } returns listOf(meal1, meal2, meal3)

        // When
        val result = useCase()

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("Healthy Salad")
        assertThat(result[1].name).isEqualTo("Healthy Burger")
        assertThat(result[0].nutrition.totalFat).isLessThan(result[1].nutrition.totalFat)
    }

    @Test
    fun `should return empty list when no meals meet criteria when repository has only unhealthy meals`() {
        // Given
        val meal1 = createMeal(
            id = 1,
            name = "Very Unhealthy Burger",
            preparationTime = 20,
            nutrition = createNutrition(
                totalFat = 100.0,
                saturatedFat = 50.0,
                carbohydrates = 200.0
            )
        )

        every { repository.getMeals() } returns listOf(meal1)

        // When
        val result = useCase()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return 2 meals when preparation time is less than or equal to 15 minutes`() {
        // Given
        val meal1 = createMeal(
            id = 1,
            name = "Quick Burger",
            preparationTime = 10,
            nutrition = createNutrition(totalFat = 10.0, saturatedFat = 4.0, carbohydrates = 20.0) // adjusted
        )
        val meal2 = createMeal(
            id = 2,
            name = "Slow Pizza",
            preparationTime = 20,
            nutrition = createNutrition(totalFat = 15.0, saturatedFat = 7.0, carbohydrates = 25.0)
        )
        val meal3 = createMeal(
            id = 3,
            name = "Healthy Salad",
            preparationTime = 12,
            nutrition = createNutrition(totalFat = 5.0, saturatedFat = 2.0, carbohydrates = 15.0)
        )

        val allMeals = listOf(meal1, meal2, meal3)

        // Given
        val mealRepository: MealRepository = mockk()
        every { mealRepository.getMeals() } returns allMeals

        val useCase = GetHealthyFastFoodMealsUseCase(mealRepository)

        // When
        val result = useCase()

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0].preparationTime).isAtMost(15)
        assertThat(result[1].preparationTime).isAtMost(15)
    }

    @Test
    fun `should return empty when all meals have carbohydrates greater than the average of all meals`() {
        // Given meals
        val meal1 = createMeal(
            id = 1,
            name = "Carby Meal 1",
            preparationTime = 10,
            nutrition = createNutrition(totalFat = 100.0, saturatedFat = 80.0, carbohydrates = 500.0)
        )
        val meal2 = createMeal(
            id = 2,
            name = "Carby Meal 2",
            preparationTime = 12,
            nutrition = createNutrition(totalFat = 101.0, saturatedFat = 81.0, carbohydrates = 501.0)
        )
        val meal3 = createMeal(
            id = 3,
            name = "Carby Meal 3",
            preparationTime = 13,
            nutrition = createNutrition(totalFat = 102.0, saturatedFat = 82.0, carbohydrates = 502.0)
        )
        val meal4 = createMeal(
            id = 3,
            name = "Carby Meal 4",
            preparationTime = 20,
            nutrition = createNutrition(totalFat = 50.0, saturatedFat = 50.0, carbohydrates = 502.0)
        )

        val allMeals = listOf(meal1, meal2, meal3, meal4)

        val mealRepository: MealRepository = mockk()
        every { mealRepository.getMeals() } returns allMeals

        val useCase = GetHealthyFastFoodMealsUseCase(mealRepository)

        // When
        val result = useCase()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty when all meals have total fat greater than the average of all meals`() {
        // Given meals
        val meal1 = createMeal(
            id = 1,
            name = "Fatty Meal 1",
            preparationTime = 10,
            nutrition = createNutrition(totalFat = 100.0, saturatedFat = 80.0, carbohydrates = 500.0)
        )
        val meal2 = createMeal(
            id = 2,
            name = "Fatty Meal 2",
            preparationTime = 12,
            nutrition = createNutrition(totalFat = 101.0, saturatedFat = 81.0, carbohydrates = 501.0)
        )
        val meal3 = createMeal(
            id = 3,
            name = "Fatty Meal 3",
            preparationTime = 13,
            nutrition = createNutrition(totalFat = 102.0, saturatedFat = 82.0, carbohydrates = 502.0)
        )
        val meal4 = createMeal(
            id = 4,
            name = "Fatty Meal 4",
            preparationTime = 20,
            nutrition = createNutrition(totalFat = 50.0, saturatedFat = 50.0, carbohydrates = 502.0)
        )

        val allMeals = listOf(meal1, meal2, meal3, meal4)

        val mealRepository: MealRepository = mockk()
        every { mealRepository.getMeals() } returns allMeals

        val useCase = GetHealthyFastFoodMealsUseCase(mealRepository)

        // When
        val result = useCase()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty when all meals have saturated fat greater than the average of all meals`() {
        // Given
        val meal1 = createMeal(
            id = 1,
            name = "Saturated Fatty Meal 1",
            preparationTime = 10,
            nutrition = createNutrition(totalFat = 100.0, saturatedFat = 80.0, carbohydrates = 500.0)
        )
        val meal2 = createMeal(
            id = 2,
            name = "Saturated Fatty Meal 2",
            preparationTime = 12,
            nutrition = createNutrition(totalFat = 101.0, saturatedFat = 81.0, carbohydrates = 501.0)
        )
        val meal3 = createMeal(
            id = 3,
            name = "Saturated Fatty Meal 3",
            preparationTime = 13,
            nutrition = createNutrition(totalFat = 102.0, saturatedFat = 82.0, carbohydrates = 502.0)
        )
        val meal4 = createMeal(
            id = 4,
            name = "Saturated Fatty Meal 4",
            preparationTime = 20,
            nutrition = createNutrition(totalFat = 50.0, saturatedFat = 50.0, carbohydrates = 502.0)
        )

        val allMeals = listOf(meal1, meal2, meal3, meal4)

        val mealRepository: MealRepository = mockk()
        every { mealRepository.getMeals() } returns allMeals

        val useCase = GetHealthyFastFoodMealsUseCase(mealRepository)

        // When
        val result = useCase()

        // Then
        assertThat(result).isEmpty()
    }
}