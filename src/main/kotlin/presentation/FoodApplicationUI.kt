package org.example.presentation

import org.example.presentation.controller.*
import org.example.presentation.io.ConsoleIO
import org.example.utils.MealAppException




class FoodApplicationUI(
    private val foodController: FoodController,
    private val io: ConsoleIO
) {
    fun start() {
        while (true) {
            displayMenu()
            when (readMenuChoice()) {
                1 -> executeWithPauseAndHandleErrors { foodController.healthyFastFoodMealsController.display() }
                2 -> executeWithPauseAndHandleErrors { foodController.mealSearchController.display() }
                3 -> executeWithPauseAndHandleErrors { foodController.iraqiMealsController.display() }
                4 -> executeWithPauseAndHandleErrors { foodController.easyFoodSuggestionController.display() }
                5 -> executeWithPauseAndHandleErrors { foodController.ingredientGameController.display() }
                6 -> executeWithPauseAndHandleErrors { foodController.sweetMealsController.displayEggFreeSweets() }
                7 -> executeWithPauseAndHandleErrors { foodController.ketoDietController.display() }
                8 -> executeWithPauseAndHandleErrors { foodController.mealsByAddDateController.display() }
                9 -> executeWithPauseAndHandleErrors { foodController.gymHelperController.display() }
                10 -> executeWithPauseAndHandleErrors { foodController.exploreFoodCultureController.display() }
                11 -> executeWithPauseAndHandleErrors { foodController.ingredientGameController.display() }
                12 -> executeWithPauseAndHandleErrors { foodController.potatoMealsController.display() }
                13 -> executeWithPauseAndHandleErrors { foodController.highCalorieMealSuggestionController.display() }
                14 -> executeWithPauseAndHandleErrors { foodController.seafoodMealsByProteinController.display() }
                15 -> executeWithPauseAndHandleErrors { foodController.italyLargeGroupMealsController.display() }
                0 -> break
                else -> io.printOutput("Invalid choice. Please try again.")
            }
        }
    }

    private fun executeWithPauseAndHandleErrors(action: () -> Unit) {
        try {
            action()
        } catch (e: MealAppException.NoMealsFoundException) {
            io.printOutput("\n⚠️ ${e.message}")
            io.printOutput("Try different search criteria or choose another option.")
        } catch (e: MealAppException.InvalidDateFormatException) {
            io.printOutput("\n⚠️ ${e.message}")
            io.printOutput("Please use the format yyyy-MM-dd (e.g., 2023-12-31)")
        } catch (e: Exception) {
            io.printOutput("\n⚠️ An unexpected error occurred: ${e.message}")
            io.printOutput("Please try again or choose a different option.")
        } finally {
            io.printOutput("\nPress Enter to continue...")
            io.readInput("")
        }
    }

    private fun displayMenu() {
        io.printOutput("""
            ╭─────────── Food Change Mood ───────────╮
            │                                        │
            │  1. Healthy Fast Food Meals           │
            │  2. Meal Search                       │
            │  3. Iraqi Meals                       │
            │  4. Easy Food Suggestions             │
            │  5. Ingredient Game                   │
            │  6. Sweets with No Eggs              │
            │  7. Keto Diet Meal Helper            │
            │  8. Meals By Add Date                │
            │  9. Gym Helper                       │
            │  10. Explore Food Culture            │
            │  11. Ingredient Game                 │
            │  12. Potato Meals                    │
            │  13. High Calorie Meals              │
            │  14. Seafood Meals By Protein        │
            │  15. Italy Large Group Meals         │
            │                                        │
            │  0. Exit                              │
            ╰────────────────────────────────────────╯
        """.trimIndent())
    }

    private fun readMenuChoice(): Int {
        return io.readInput("Enter your choice (0-15): ")?.toIntOrNull() ?: -1
    }
}