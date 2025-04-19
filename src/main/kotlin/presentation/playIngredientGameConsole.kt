package org.example.presentation

import org.example.logic.PlayIngredientGameUseCase

fun playIngredientGameConsole(gameUseCase: PlayIngredientGameUseCase) {
    val result = gameUseCase.invoke { meal, options ->
        println("Guess an ingredient in '${meal.name}':")

        options.forEachIndexed { index, option ->
            println("${index + 1}. $option")
        }

        print("Enter your choice (1-${options.size}): ")
        val choice = readlnOrNull()?.toIntOrNull()

        when {
            choice == null -> {
                println("Invalid input. Please enter a number.")
                null
            }
            choice !in 1..options.size -> {
                println("Please enter a number between 1 and ${options.size}.")
                null
            }
            else -> options[choice - 1]
        }
    }

    println("Game Over! Final Score: ${result.finalScore}, Correct Answers: ${result.correctAnswers}, Reason: ${result.message}")
}