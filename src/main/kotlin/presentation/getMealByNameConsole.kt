package org.example.presentation

import org.example.logic.GetMealsByNameUseCase

fun getMealsByNameConsole(getMealsByNameUseCase: GetMealsByNameUseCase) {
    print("Search meals: ")
    val query = readlnOrNull()?.trim() ?: ""

    val meals = getMealsByNameUseCase(query)

    if (meals.isEmpty()) {
        println("No meals found for '$query'")
    } else {
        println("\nFound ${meals.size} meals:")
        meals.forEachIndexed { index, meal ->
            println(
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
