package org.example.presentation

import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.GetRandomMealUseCase

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

fun guessGame(
    getRandomMealUseCase: GetRandomMealUseCase
) {
    val attempts = 3
    val randomMeal = getRandomMealUseCase()
    val correctTime = randomMeal.preparationTime
    var remainingAttempts = attempts

    println("Guess the preparation time (in minutes) for the meal: ${randomMeal.name}")

    while (remainingAttempts > 0) {
        print("Enter your guess ($remainingAttempts attempts left): ")
        val userInput = readLine()

        // Validate user input
        val guessedTime = userInput?.toIntOrNull()
        if (guessedTime == null) {
            println("Please enter a valid number.")
            continue
        }

        when {
            guessedTime < correctTime -> {
                println("Too low! Try again.")
            }
            guessedTime > correctTime -> {
                println("Too high! Try again.")
            }
            else -> {
                println("Congratulations! You guessed the correct time: $correctTime minutes.")
                return // Exit the function if the guess is correct
            }
        }

        remainingAttempts--
    }

    // If the user runs out of attempts
    println("Sorry! You've used all your attempts. The correct preparation time was: $correctTime minutes.")
}