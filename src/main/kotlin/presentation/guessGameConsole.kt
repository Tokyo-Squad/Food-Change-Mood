package org.example.presentation

import org.example.logic.GetRandomMealUseCase

fun guessGameConsole(attempts: Int = 3, randomMealUseCase: GetRandomMealUseCase) {
    val meal = randomMealUseCase.invoke()
    var tries = attempts

    println("\n=== Time Guess: ${meal.name} ===")

    while (tries-- > 0) {
        print("Guess mins ($tries tries left): ")
        when (val guess = readLine()?.toIntOrNull()) {
            null -> println("Numbers only!")
            meal.preparationTime -> return println("Correct! ${meal.preparationTime} mins.")
            else -> println("Too ${if (guess < meal.preparationTime) "low" else "high"}!")
        }
    }
    println("Game Over! Answer: ${meal.preparationTime} mins.")
}