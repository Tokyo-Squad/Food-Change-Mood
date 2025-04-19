package org.example.presentation

import org.example.logic.ExploreCountriesFoodCultureUseCase

fun exploreFoodCultureConsole(useCase: ExploreCountriesFoodCultureUseCase) {
    print("\n=== Food Culture Explorer ===\nEnter country name: ")
    val countryInput = readlnOrNull()?.trim() ?: return
    val meals = useCase(countryInput)

    if (meals.isEmpty()) {
        println("No meals found for this country.")
        return
    }

    println("\nFound ${meals.size} ${countryInput.lowercase()} meals:")
    meals.forEachIndexed { i, meal ->
        println("${i + 1}. ${meal.name} | ${meal.preparationTime} mins")
        println("   Ingredients: ${meal.ingredients.take(3).joinToString()}...")
    }
}