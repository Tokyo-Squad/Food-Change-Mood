package org.example.presentation.controller

import org.example.logic.GymHelperUseCase
import org.example.presentation.io.ConsoleIO

class GymHelperController(
    private val io: ConsoleIO,
    private val gymHelper: GymHelperUseCase
) {
    fun display() {
        io.printOutput("\n=== GYM MEAL PLANNER ===")


        // Read target details from the user.
        val targetCalories = io.readInput("Enter target calories: ")?.toFloatOrNull()
        val targetProtein = io.readInput("Enter target protein (grams): ")?.toFloatOrNull()

        try {
            if (targetCalories != null && targetProtein != null) {
                io.printOutput("\nSuggested Meals matching your goals:")
                io.printOutput("Target: ${targetCalories}kcal | ${targetProtein}g protein\n")

                // Invoke the use case and iterate over each returned meal.
                gymHelper.invoke(targetCalories = targetCalories, targetProtein = targetProtein)
                    .forEachIndexed { index, meal ->
                        // Build and print out the meal information.
                        val message = """
                        ${index + 1}. ${meal.name.capitalize()}
                           • Calories: ${meal.nutrition.calories}kcal
                           • Protein: ${meal.nutrition.protein}g
                           • Carbs: ${meal.nutrition.carbohydrates}g
                           • Fat: ${meal.nutrition.totalFat}g
                           • Time to prepare: ${meal.preparationTime}mins
                    """.trimIndent()
                        io.printOutput(message)
                    }
            } else {
                throw IllegalArgumentException("Please enter valid numbers for calories and protein")
            }
        } catch (e: NumberFormatException) {
            io.printOutput("\n⚠ Error: Please enter valid numbers only")
        } catch (e: IllegalArgumentException) {
            io.printOutput("\n⚠ Error: ${e.message}")
        }
    }
}
