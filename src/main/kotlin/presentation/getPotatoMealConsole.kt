package org.example.presentation

import org.example.logic.GetRandomPotatoMealsUseCase

fun getPotatoMealsConsole(useCase: GetRandomPotatoMealsUseCase) {
    println("\n=== Potato-Based Meals ===")

    val potatoMeals = useCase()

    if (potatoMeals.isEmpty()) {
        println("No potato meals found.")
        return
    }

    potatoMeals.forEachIndexed { i, meal ->
        println("${i + 1}. ${meal.name} | ${meal.preparationTime}mins")
    }
}
