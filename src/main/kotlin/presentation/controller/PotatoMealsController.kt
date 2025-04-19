package org.example.presentation.controller

import org.example.logic.GetRandomPotatoMealsUseCase
import org.example.presentation.io.ConsoleIO

class PotatoMealsController(
    private val io: ConsoleIO,
    private val useCase: GetRandomPotatoMealsUseCase,
) {
    fun display() {
        io.printOutput("\n=== Potato-Based Meals ===")

        val potatoMeals = useCase()

        if (potatoMeals.isEmpty()) {
            io.printOutput("No potato meals found.")
            return
        }

        // Print each potato meal with index information.
        potatoMeals.forEachIndexed { index, meal ->
            io.printOutput("${index + 1}. ${meal.name} | ${meal.preparationTime}mins")
        }
    }
}
