package org.example.presentation.controller

import org.example.logic.usecase.GetRandomMealUseCase
import org.example.presentation.io.ConsoleIO

class GuessGameController(
    private val io: ConsoleIO,
    private val getRandomMealUseCase: GetRandomMealUseCase
) {
    fun playGame(attempts: Int = 3) {
        val meal = getRandomMealUseCase()
        var tries = attempts

        io.printOutput("\n=== Time Guess: ${meal.name} ===")

        while (tries > 0) {
            // Ask the user for the guess.
            val input = io.readInput("Guess mins ($tries tries left): ")
            tries--
            when (val guess = input?.toIntOrNull()) {
                null -> io.printOutput("Numbers only!")
                meal.preparationTime -> {
                    io.printOutput("Correct! ${meal.preparationTime} mins.")
                    return
                }
                else -> {
                    val hint = if (guess < meal.preparationTime) "low" else "high"
                    io.printOutput("Too $hint!")
                }
            }
        }
        io.printOutput("Game Over! Answer: ${meal.preparationTime} mins.")
    }
}
