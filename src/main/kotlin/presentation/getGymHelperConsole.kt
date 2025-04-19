package org.example.presentation

import org.example.logic.GymHelperUseCase

fun getGymHelperConsole(gymHelper: GymHelperUseCase) {
    println("\n=== GYM MEAL PLANNER ===")
    print("Enter target calories: ")
    val targetCalories = readln().toFloatOrNull()
    print("Enter target protein (grams): ")
    val targetProtein = readln().toFloatOrNull()

    try {
        if (targetCalories != null && targetProtein != null) {
            println("\nSuggested Meals matching your goals:")
            println("Target: ${targetCalories}kcal | ${targetProtein}g protein\n")

            gymHelper.invoke(targetCalories = targetCalories, targetProtein = targetProtein)
                .forEachIndexed { index, meal ->
                    println(
                        """
                        ${index + 1}. ${meal.name.capitalize()}
                           • Calories: ${meal.nutrition.calories}kcal
                           • Protein: ${meal.nutrition.protein}g
                           • Carbs: ${meal.nutrition.carbohydrates}g
                           • Fat: ${meal.nutrition.totalFat}g
                           • Time to prepare: ${meal.preparationTime}mins
                    """.trimIndent()
                    )
                }
        } else {
            throw IllegalArgumentException("Please enter valid numbers for calories and protein")
        }
    } catch (e: NumberFormatException) {
        println("\n⚠ Error: Please enter valid numbers only")
    } catch (e: IllegalArgumentException) {
        println("\n⚠ Error: ${e.message}")
    }
}