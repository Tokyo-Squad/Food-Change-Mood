package org.example.presentation

import org.example.logic.EasyFoodSuggestionUseCase

fun getEasyFoodSuggestionConsole(useCase: EasyFoodSuggestionUseCase) {
    println("\n=== Easy Meals (≤30mins, ≤5 ingredients, ≤6 steps) ===")

    useCase.getEasyMeals().let { meals ->
        if (meals.isEmpty()) println("No easy meals found.")
        else meals.forEachIndexed { i, meal ->
            println("${i + 1}. ${meal.name} | ${meal.preparationTime}mins | ${meal.numberOfIngredients}ing | ${meal.numberOfSteps}steps")
        }
    }
}

