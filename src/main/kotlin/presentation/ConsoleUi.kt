package org.example.presentation

import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.HighCalorieMealSuggestionUseCase

fun mealsByAddDateConsole(getMealsByAddDateUseCase: GetMealsByAddDateUseCase) {
    println("Enter a date (yyyy-MM-dd) to search for meals:")
    val dateInput = readLine() ?: ""

    getMealsByAddDateUseCase(dateInput)
        .onSuccess { meals ->
            println("\nMeals added on $dateInput:")
            meals.forEach { meal ->
                println("ID: ${meal.id}, Name: ${meal.name}")
            }

            val mealsMap = meals.associateBy { it.id }

            println("\nEnter a meal ID to view more details (or press Enter to exit):")
            val selectedId = readLine()?.toIntOrNull()

            if (selectedId != null) {
                mealsMap[selectedId]?.let { selectedMeal ->
                    viewMoreDetailsAboutSpecificMeal(selectedMeal)
                } ?: println("Meal with ID $selectedId not found in the current list.")
            }}
            .onFailure {
                println(it.message)
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