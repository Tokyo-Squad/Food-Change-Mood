package org.example.presentation.controller

import org.example.logic.usecase.GetSeafoodMealsSortedByProteinUseCase
import org.example.presentation.io.ConsoleIO

class SeafoodMealsByProteinController(
    private val io: ConsoleIO,
    private val useCase: GetSeafoodMealsSortedByProteinUseCase,
) {
    fun display() {
        io.printOutput("\n=== Seafood Meals by Protein Content ===")

        val seafoodMeals = useCase.invoke()

        if (seafoodMeals.isEmpty()) {
            io.printOutput("No seafood meals found.")
            return
        }

        seafoodMeals.forEach { (rank, meal) ->
            io.printOutput("$rank. ${meal.name} | ${meal.nutrition.protein}g protein")
        }
    }
}
