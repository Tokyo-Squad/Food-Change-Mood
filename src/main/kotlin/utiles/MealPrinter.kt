package org.example.utiles

import org.example.model.FormattedMeal

class MealPrinter {
    fun printMeal(meal: FormattedMeal, index: Int){
        println("\n${index + 1}. ${meal.name}")
        println("Preparation time: ${meal.preparationTime}")
        println("\n Ingredients:")
        println(meal.ingredients)
        println("\nSteps:")
        println(meal.steps)
        println("\n Nutrition (per serving):")
        println(meal.nutritionSummary)
        println("\n Tags: ${meal.tags}")
        println("────────────────────────────")
    }
}