package org.example.presentation

import org.example.logic.GetSeafoodMealsSortedByProteinUseCase

fun getSeafoodMealsByProteinConsole(useCase: GetSeafoodMealsSortedByProteinUseCase) {
    println("\n=== Seafood Meals by Protein Content ===")

    val seafoodMeals = useCase()

    if (seafoodMeals.isEmpty()) {
        println("No seafood meals found.")
        return
    }

    seafoodMeals.forEach { (rank, meal) ->
        println("$rank. ${meal.name} | ${meal.nutrition.protein}g protein")
    }
}
