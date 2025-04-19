package org.example.presentation

import org.example.logic.HighCalorieMealSuggestionUseCase


fun getHighCalorieMealSuggestionConsole(useCase: HighCalorieMealSuggestionUseCase) {
    println("\n=== High-Calorie Meals (>700 cal) ===")

    useCase.getNextSuggestion()?.let { meal ->
        println("${meal.name} (${meal.nutrition.calories} cal)")
        print("View details (Y/N)? ")

        when (readLine()?.firstOrNull()?.uppercase()) {
            "Y" -> viewMoreDetailsAboutSpecificMealConsole(meal)
            "N" -> getHighCalorieMealSuggestionConsole(useCase)
            else -> println("Invalid choice")
        }
    } ?: println("No more high-calorie meals available.")
}
