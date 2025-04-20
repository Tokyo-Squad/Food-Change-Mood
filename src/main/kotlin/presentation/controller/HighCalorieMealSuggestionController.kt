package org.example.presentation.controller

import org.example.logic.usecase.HighCalorieMealSuggestionUseCase
import org.example.presentation.io.ConsoleIO
import viewMoreDetailsAboutSpecificMeal

class HighCalorieMealSuggestionController(
    private val io: ConsoleIO,
    private val useCase: HighCalorieMealSuggestionUseCase
) {
    fun display() {
        io.printOutput("\n=== High-Calorie Meals (>700 cal) ===")


        val meal = useCase.getNextSuggestion()
        if (meal == null) {
            io.printOutput("No more high-calorie meals available.")
            return
        }
        io.printOutput("${meal.name} (${meal.nutrition.calories} cal)")

        val answer = io.readInput("View details (Y/N)? ")?.firstOrNull()?.uppercase()
        when (answer) {
            "Y" -> viewMoreDetailsAboutSpecificMeal(meal)
            "N" -> display() // Recursively call display to fetch another suggestion
            else -> io.printOutput("Invalid choice")
        }
    }
}
