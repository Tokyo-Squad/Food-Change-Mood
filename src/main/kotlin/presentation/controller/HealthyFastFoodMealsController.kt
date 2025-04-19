package org.example.presentation.controller

import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.presentation.io.ConsoleIO

class HealthyFastFoodMealsController(
    private val io: ConsoleIO,
    private val useCase: GetHealthyFastFoodMealsUseCase
) {
    fun display() {
        io.printOutput("\n=== Healthy Fast Food Meals (15 mins or less) ===")
        val result = useCase()


        if (result.isEmpty()) {
            io.printOutput("No healthy fast food meals found.")
        } else {
            io.printOutput("Healthy Fast Food Meals:")
            result.forEach { meal ->
                io.printOutput("ID: ${meal.id}, Name: ${meal.name}, Time: ${meal.preparationTime} mins")
            }
        }
    }
}
