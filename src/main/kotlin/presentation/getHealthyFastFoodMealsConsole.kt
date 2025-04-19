package org.example.presentation

import org.example.logic.GetHealthyFastFoodMealsUseCase

fun getHealthyFastFoodMealsConsole(useCase: GetHealthyFastFoodMealsUseCase) {
    println("\n=== Healthy Fast Food Meals (15 mins or less) ===")

    val result = useCase.invoke()

    if (result.isEmpty()) {
        println("No healthy fast food meals found.")
    } else {
        println("Healthy Fast Food Meals:")
        result.forEach { meal ->
            println("ID: ${meal.id}, Name: ${meal.name}, Time: ${meal.preparationTime} mins")
        }
    }
}
