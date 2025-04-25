package presentation.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.datetime.LocalDate
import org.example.logic.usecase.GetRandomMealUseCase
import org.example.model.Meal
import org.example.model.Nutrition
import org.example.presentation.controller.GuessGameController
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

class GuessGameControllerTest {

    private lateinit var io: ConsoleIO
    private lateinit var getRandomMealUseCase: GetRandomMealUseCase
    private lateinit var controller: GuessGameController

    private val testMeal = Meal(
        id = 1,
        name = "Test Meal",
        contributorId = 1,
        description = "desc",
        ingredients = listOf("ing1"),
        numberOfIngredients = 1,
        numberOfSteps = 1,
        nutrition = Nutrition(0.0,0.0,0.0,0.0,0.0,0.0,0.0),
        preparationTime = 30,
        steps = listOf("step1"),
        submitted = LocalDate(2024, 1, 1),
        tags = listOf("tag")
    )

    @BeforeEach
    fun setup() {
        io = mockk(relaxed = true)
        getRandomMealUseCase = mockk()
        controller = GuessGameController(io, getRandomMealUseCase)

        every { getRandomMealUseCase.invoke() } returns testMeal
    }

    @Test
    fun `should end with correct message when guess is correct on first attemp`() {
        every { io.readInput(any()) } returns "30"

        controller.playGame(attempts = 3)

        verifySequence {
            io.printOutput("\n=== Time Guess: Test Meal ===")
            io.readInput("Guess mins (3 tries left): ")
            io.printOutput("Correct! 30 mins.")
        }
    }
    @Test
    fun `should end with correct message when guess is correct on second attempt`() {
        every { io.readInput(any()) } returnsMany listOf("20", "30")

        controller.playGame(attempts = 3)

        verifySequence {
            io.printOutput("\n=== Time Guess: Test Meal ===")
            io.readInput("Guess mins (3 tries left): ")
            io.printOutput("Too low!")
            io.readInput("Guess mins (2 tries left): ")
            io.printOutput("Correct! 30 mins.")
        }
    }

    @Test
    fun `should end with correct message when guess is correct on third attempt`() {
        every { io.readInput(any()) } returnsMany listOf("20", "40", "30")

        controller.playGame(attempts = 3)

        verifySequence {
            io.printOutput("\n=== Time Guess: Test Meal ===")
            io.readInput("Guess mins (3 tries left): ")
            io.printOutput("Too low!")
            io.readInput("Guess mins (2 tries left): ")
            io.printOutput("Too high!")
            io.readInput("Guess mins (1 tries left): ")
            io.printOutput("Correct! 30 mins.")
        }
    }
    @Test
    fun `should give hints and end after all attempts when guesses are incorrect`() {
        every { io.readInput(any()) } returnsMany listOf("20", "40", "25")

        controller.playGame(attempts = 3)

        verifySequence {
            io.printOutput("\n=== Time Guess: Test Meal ===")
            io.readInput("Guess mins (3 tries left): ")
            io.printOutput("Too low!")
            io.readInput("Guess mins (2 tries left): ")
            io.printOutput("Too high!")
            io.readInput("Guess mins (1 tries left): ")
            io.printOutput("Too low!")
            io.printOutput("Game Over! Answer: 30 mins.")
        }
    }

    @Test
    fun `should handle non-numeric input when user enters invalid guess`() {
        every { io.readInput(any()) } returnsMany listOf("abc", "30")

        controller.playGame(attempts = 3)

        verifySequence {
            io.printOutput("\n=== Time Guess: Test Meal ===")
            io.readInput("Guess mins (3 tries left): ")
            io.printOutput("Numbers only!")
            io.readInput("Guess mins (2 tries left): ")
            io.printOutput("Correct! 30 mins.")
        }
    }
}
