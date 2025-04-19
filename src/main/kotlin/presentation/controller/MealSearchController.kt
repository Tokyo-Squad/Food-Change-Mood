package org.example.presentation.controller

import org.example.logic.GetMealsByNameUseCase
import org.example.presentation.io.ConsoleIO

class MealSearchController(
    private val io: ConsoleIO,
    private val getMealsByNameUseCase: GetMealsByNameUseCase
) {
    fun display() {
        val query = io.readInput("Search meals: ")?.trim() ?: ""
        val meals = getMealsByNameUseCase(query)

        if (meals.isEmpty()) {
            io.printOutput("No meals found for '$query'")
        } else {
            io.printOutput("\nFound ${meals.size} meals:")
            meals.forEachIndexed { index, meal ->
                io.printOutput(
                    """
                ${index + 1}. ${meal.name}
                   Time: ${meal.preparationTime} min
                   Ingredients: ${meal.numberOfIngredients}
                   Steps: ${meal.numberOfSteps}
                   Tags: ${meal.tags.take(3).joinToString()}
                """.trimIndent()
                )
            }
        }
    }
}
