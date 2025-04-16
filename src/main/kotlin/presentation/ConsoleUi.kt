package org.example.presentation

import org.example.logic.GetMealsByAddDateUseCase

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
            }
        }
        .onFailure { error ->
            println("\nError: ${error.message}")
        }
}