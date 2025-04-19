package org.example.presentation.controller

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.presentation.io.ConsoleIO

class EasyFoodSuggestionController(
    private val io: ConsoleIO,
    private val useCase: EasyFoodSuggestionUseCase
) {
    fun display() {
        io.printOutput("\n=== Easy Meals (≤30mins, ≤5 ingredients, ≤6 steps) ===")


        val meals = useCase.getEasyMeals()
        if(meals.isEmpty()){
            io.printOutput("No easy meals found.")
        } else {
            meals.forEachIndexed { i, meal ->
                io.printOutput(
                    "${i + 1}. ${meal.name} | ${meal.preparationTime} mins | " +
                            "${meal.numberOfIngredients} ingredients | ${meal.numberOfSteps} steps"
                )
            }
        }
    }
}