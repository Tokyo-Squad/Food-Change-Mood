package org.example.presentation.controller

import org.example.logic.usecase.PlayIngredientGameUseCase
import org.example.presentation.io.ConsoleIO

class IngredientGameController(
    private val io: ConsoleIO,
    private val gameUseCase: PlayIngredientGameUseCase
) {
    fun display() {
        val result = gameUseCase { meal, options ->
            io.printOutput("Guess an ingredient in '${meal.name}':")
            options.forEachIndexed { index, option ->
                io.printOutput("${index + 1}. $option")
            }

            val choiceInput = io.readInput("Enter your choice: ")
            val choice = choiceInput?.toIntOrNull()

            options.getOrNull((choice ?: 1) - 1)
        }

        io.printOutput("Game Over! Final Score: ${result.finalScore}, Correct Answers: ${result.correctAnswers}, Reason: ${result.message}")
    }
}
