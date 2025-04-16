package org.example.presentation

import org.example.logic.GetRandomMealUseCase

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