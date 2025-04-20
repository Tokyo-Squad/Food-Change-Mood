package org.example.presentation.controller

import org.example.logic.usecase.GetIraqiMealsUseCase
import org.example.presentation.io.ConsoleIO

class IraqiMealsController(
    private val io: ConsoleIO,
    private val iraqiMealUseCase: GetIraqiMealsUseCase
) {
    fun display() {
        io.printOutput("\n=== Iraqi Meals ===")


        val iraqiMeals = iraqiMealUseCase.invoke()

        if (iraqiMeals.isEmpty()) {
            io.printOutput("No Iraqi meals found.")
            return
        }

        iraqiMeals.forEachIndexed { index, meal ->
            io.printOutput("${index + 1}. ${meal.name} | ${meal.preparationTime} mins")
        }
    }
}
