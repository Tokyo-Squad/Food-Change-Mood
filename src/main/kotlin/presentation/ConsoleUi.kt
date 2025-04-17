package org.example.presentation

import org.example.logic.CsvRepository
import org.example.logic.GetLargeGroupItalyMealUseCase
import org.example.logic.GetMealByIdUseCase
import org.example.logic.CsvRepository
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.PlayIngredientGameUseCase
import org.example.logic.HighCalorieMealSuggestionUseCase
import org.example.logic.*

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

fun getHighCalorieMealSuggestionConsole(highCalorieMealSuggestionUseCase: HighCalorieMealSuggestionUseCase) {
    val suggestion = highCalorieMealSuggestionUseCase.getNextSuggestion()
    if (suggestion == null) {
        println("No more high-calorie meals available to suggest.")
        return
    }
    println("High-Calorie Meal Suggestion: ${suggestion.name}")
    println("Enter 'yes' to view details, 'no' for another suggestion")

    when (readLine()?.trim()?.lowercase()) {
        "yes", "y" -> {
            println("Meal Details:")
            println("Name       : ${suggestion.name}")
            println("Description: ${suggestion.description}")
            println("Calories   : ${suggestion.nutrition.calories}")
        }

        "no", "n" -> {
            getHighCalorieMealSuggestionConsole(highCalorieMealSuggestionUseCase)
        }

        else -> {
            println("Invalid input.")
        }
    }
}

fun showItalyLargeGroupMeals(csvRepository : CsvRepository){
    val largeGroupItalyMealUseCase = GetLargeGroupItalyMealUseCase(csvRepository)
    val italianLargeGroupItalyMeal = largeGroupItalyMealUseCase.getLargeGroupItalyMeal()
    italianLargeGroupItalyMeal.forEachIndexed { index, meal ->
        println("${index+1} - $meal")
    }

fun getHealthyFastFoodMealsConsole(useCase: GetHealthyFastFoodMealsUseCase) {
    println("Fetching healthy fast food meals (under 15 mins, low fat/carbs)...")

    val result = useCase.getHealthyFastFoodMeals()

    if (result.isEmpty()) {
        println("No healthy fast food meals found.")
    } else {
        println("Healthy Fast Food Meals:")
        result.forEach { meal ->
            println("ID: ${meal.id}, Name: ${meal.name}, Time: ${meal.preparationTime} mins")
        }
    }
}