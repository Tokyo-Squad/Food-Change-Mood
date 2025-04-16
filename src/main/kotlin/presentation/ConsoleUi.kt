package org.example.presentation

import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase

fun getMealsByDateConsole(getMealsByAddDateUseCase: GetMealsByAddDateUseCase) {
    println("Enter a date (yyyy-MM-dd) to search for meals:")
    val dateInput = readLine() ?: ""
    val result = getMealsByAddDateUseCase(dateInput)
    result.onSuccess {
        println("Meals added on $dateInput:")
        result.getOrNull()?.forEach { (id, name) -> println("ID: $id, Name: $name") }
    }.onFailure {
        println(result.exceptionOrNull()?.message)
    }
}

fun getMealByIdConsole(getMealByIdUseCase: GetMealByIdUseCase) {
    println("Enter a meal ID to retrieve details:")
    val idInput = readLine()?.toIntOrNull()

    if (idInput != null) {
        val result = getMealByIdUseCase(idInput)
            .onSuccess {
                it.let {
                    viewMoreDetailsAboutSpecificMeal(it)
                }
            }
            .onFailure {
                println(it.message)
            }
    } else {
        println("Invalid input. Please enter a valid meal ID.")
    }
}

fun playIngredientGame(gameUseCase: PlayIngredientGameUseCase) {
    // Play the game
    val result = gameUseCase.playGame { meal, options ->
        println("Guess an ingredient in '${meal.name}':")

        // Display the options to the user
        options.forEachIndexed { i, option -> println("${i + 1}. $option") }

        // Read the user's choice (e.g., via console input)
        val choice = readlnOrNull()?.toIntOrNull()

        // Return the selected ingredient, or null if the input is invalid
        options.getOrNull((choice ?: 1) - 1) // Default to the first option if invalid
    }

    // Print the final game result
    println("Game Over! Final Score: ${result.first}, Correct Answers: ${result.second}, Reason: ${result.third}")
}