package org.example.presentation.controller

import org.example.logic.usecase.GetMealsByAddDateUseCase
import org.example.presentation.io.ConsoleIO
import viewMoreDetailsAboutSpecificMeal

class MealsByAddDateController(
    private val io: ConsoleIO, private val getMealsByAddDateUseCase: GetMealsByAddDateUseCase
) {
    fun display() {
        val dateInput = io.readInput("Date (yyyy-MM-dd): ") ?: ""

        getMealsByAddDateUseCase(dateInput).fold(onSuccess = { meals ->

            meals.forEach { meal ->
                io.printOutput("${meal.id}: ${meal.name}")
            }

            val selectedMealId = io.readInput("\nID for details: ")?.toIntOrNull()
            val selectedMeal = selectedMealId?.let { id ->
                meals.find { it.id == id }
            }

            if (selectedMeal != null) {
                viewMoreDetailsAboutSpecificMeal(selectedMeal)
            } else {
                io.printOutput("Invalid ID")
            }
        }, onFailure = { error ->
            io.printOutput(error.message ?: "An error occurred")
        })
    }
}
