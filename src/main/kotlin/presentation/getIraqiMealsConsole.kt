package org.example.presentation

import org.example.logic.GetIraqiMealsUseCase

fun getIraqiMealsConsole(iraqiMealUseCase: GetIraqiMealsUseCase) {
    println("\n=== Iraqi Meals ===")

    val iraqiMeals = iraqiMealUseCase.invoke()

    if (iraqiMeals.isEmpty()) {
        println("No Iraqi meals found.")
        return
    }

    iraqiMeals.forEachIndexed { index, meal ->
        println("${index + 1}. ${meal.name} | ${meal.preparationTime}mins")
    }
}