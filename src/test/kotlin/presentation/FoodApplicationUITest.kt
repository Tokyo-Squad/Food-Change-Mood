package org.example.presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FoodApplicationUITest {
    private lateinit var foodController: FoodController
    private lateinit var consoleIO: ConsoleIO
    private lateinit var foodApplicationUI: FoodApplicationUI

    @BeforeEach
    fun setUp() {
        foodController = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true) {
            every { readInput(any()) } returns "0"
            every { printOutput(any()) } returns Unit
        }
        foodApplicationUI = FoodApplicationUI(foodController, consoleIO)
    }

    @Test
    fun `should call healthy fast food meals controller when option 1 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "1" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.healthyFastFoodMealsController.display() }
    }

    @Test
    fun `should call meal search controller when option 2 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "2" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.mealSearchController.display() }
    }

    @Test
    fun `should call iraqi meals controller when option 3 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "3" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.iraqiMealsController.display() }
    }

    @Test
    fun `should call easy food suggestion controller when option 4 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "4" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.easyFoodSuggestionController.display() }
    }

    @Test
    fun `should call ingredient game controller when option 5 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "5" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.ingredientGameController.display() }
    }

    @Test
    fun `should call sweet meals controller when option 6 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "6" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.sweetMealsController.displayEggFreeSweets() }
    }

    @Test
    fun `should call keto diet controller when option 7 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "7" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.ketoDietController.display() }
    }

    @Test
    fun `should call meals by add date controller when option 8 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "8" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.mealsByAddDateController.display() }
    }

    @Test
    fun `should call gym helper controller when option 9 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "9" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.gymHelperController.display() }
    }

    @Test
    fun `should call explore food culture controller when option 10 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "10" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.exploreFoodCultureController.display() }
    }

    @Test
    fun `should call ingredient game controller when option 11 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "11" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.ingredientGameController.display() }
    }

    @Test
    fun `should call potato meals controller when option 12 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "12" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.potatoMealsController.display() }
    }

    @Test
    fun `should call high calorie meal suggestion controller when option 13 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "13" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.highCalorieMealSuggestionController.display() }
    }

    @Test
    fun `should call seafood meals by protein controller when option 14 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "14" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.seafoodMealsByProteinController.display() }
    }

    @Test
    fun `should call italy large group meals controller when option 15 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "15" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { foodController.italyLargeGroupMealsController.display() }
    }

    @Test
    fun `should exit when option 0 is selected`() {
        // Given
        every { consoleIO.readInput(any()) } returns "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 0) {
            foodController.healthyFastFoodMealsController.display()
            foodController.mealSearchController.display()
            foodController.iraqiMealsController.display()
            foodController.easyFoodSuggestionController.display()
            foodController.ingredientGameController.display()
            foodController.sweetMealsController.displayEggFreeSweets()
            foodController.ketoDietController.display()
            foodController.mealsByAddDateController.display()
            foodController.gymHelperController.display()
            foodController.exploreFoodCultureController.display()
            foodController.potatoMealsController.display()
            foodController.highCalorieMealSuggestionController.display()
            foodController.seafoodMealsByProteinController.display()
            foodController.italyLargeGroupMealsController.display()
        }
    }

    @Test
    fun `should handle invalid input`() {
        // Given
        every { consoleIO.readInput(any()) } returns "invalid" andThen "0"

        // When
        foodApplicationUI.start()

        // Then
        verify(exactly = 1) { consoleIO.printOutput("Invalid choice. Please try again.") }
    }
}