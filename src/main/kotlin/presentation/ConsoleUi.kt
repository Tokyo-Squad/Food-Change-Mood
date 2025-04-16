package org.example.presentation


import org.example.logic.CsvRepository
import org.example.logic.GetMealsByAddDateUseCase

fun mealsByAddDateConsole(getMealsByAddDateUseCase: GetMealsByAddDateUseCase) {
    println("Enter a date (yyyy-MM-dd) to search for meals:")
    val dateInput = readLine() ?: ""

    getMealsByAddDateUseCase(dateInput).onSuccess { meals ->
            println("\nMeals added on $dateInput:")
            meals.forEach { meal ->
                println("ID: ${meal.id}, Name: ${meal.name}")
            }

            val mealsMap = meals.associateBy { it.id }

            println("\nEnter a meal ID to view more details (or press Enter to exit):")
            val selectedId = readLine()?.toIntOrNull()
            mealsMap[selectedId]?.let { selectedMeal ->
                viewMoreDetailsAboutSpecificMeal(selectedMeal)
            } ?: println("Meal with ID $selectedId not found in the current list.")

        }.onFailure {
            println(it.message)
        }

}


fun guessGame(
    attempts: Int = 3, repo: CsvRepository
) {

    val randomMeal = repo.getMeals().random()
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

}


