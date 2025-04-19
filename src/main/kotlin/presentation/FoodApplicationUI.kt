package org.example.presentation

import org.example.presentation.controller.*
import org.example.utils.InvalidDateFormatException
import org.example.utils.NoMealsFoundException


class FoodApplicationUI(
    private val easyFoodSuggestionController: EasyFoodSuggestionController,
    private val exploreFoodCultureController: ExploreFoodCultureController,
    private val gymHelperController: GymHelperController,
    private val healthyFastFoodMealsController: HealthyFastFoodMealsController,
    private val highCalorieMealSuggestionController: HighCalorieMealSuggestionController,
    private val ingredientGameController: IngredientGameController,
    private val iraqiMealsController: IraqiMealsController,
    private val italyLargeGroupMealsController: ItalyLargeGroupMealsController,
    private val ketoDietController: KetoDietController,
    private val mealsByAddDateController: MealsByAddDateController,
    private val mealSearchController: MealSearchController,
    private val potatoMealsController: PotatoMealsController,
    private val seafoodMealsByProteinController: SeafoodMealsByProteinController,
    private val sweetMealsController: SweetMealsController
) {
    fun start() {
        while (true) {
            displayMenu()
            when (readMenuChoice()) {
                1 -> executeWithPauseAndHandleErrors { healthyFastFoodMealsController.display() }
                2 -> executeWithPauseAndHandleErrors { mealSearchController.display() }
                3 -> executeWithPauseAndHandleErrors { iraqiMealsController.display() }
                4 -> executeWithPauseAndHandleErrors { easyFoodSuggestionController.display() }
                5 -> executeWithPauseAndHandleErrors { ingredientGameController.display() }
                6 -> executeWithPauseAndHandleErrors { sweetMealsController.displayEggFreeSweets() }
                7 -> executeWithPauseAndHandleErrors { ketoDietController.display() }
                8 -> executeWithPauseAndHandleErrors { mealsByAddDateController.display() }
                9 -> executeWithPauseAndHandleErrors { gymHelperController.display() }
                10 -> executeWithPauseAndHandleErrors { exploreFoodCultureController.display() }
                11 -> executeWithPauseAndHandleErrors { ingredientGameController.display() }
                12 -> executeWithPauseAndHandleErrors { potatoMealsController.display() }
                13 -> executeWithPauseAndHandleErrors { highCalorieMealSuggestionController.display() }
                14 -> executeWithPauseAndHandleErrors { seafoodMealsByProteinController.display() }
                15 -> executeWithPauseAndHandleErrors { italyLargeGroupMealsController.display() }
                0 -> break
                else -> println("Invalid choice. Please try again.")
            }
        }
    }

    private fun executeWithPauseAndHandleErrors(action: () -> Unit) {
        try {
            action()
        } catch (e: NoMealsFoundException) {
            println("\n⚠️ ${e.message}")
            println("Try different search criteria or choose another option.")
        } catch (e: InvalidDateFormatException) {
            println("\n⚠️ ${e.message}")
            println("Please use the format yyyy-MM-dd (e.g., 2023-12-31)")
        } catch (e: Exception) {
            println("\n⚠️ An unexpected error occurred: ${e.message}")
            println("Please try again or choose a different option.")
        } finally {
            println("\nPress Enter to continue...")
            readLine()
        }
    }


    private fun displayMenu() {
        println("""
            Food Application Menu:
            1. Easy Food Suggestions
            2. Explore Food Culture
            3. Gym Helper
            4. Healthy Fast Food Meals
            5. High Calorie Meal Suggestions
            6. Ingredient Game
            7. Iraqi Meals
            8. Italy Large Group Meals
            9. Keto Diet
            10. Meals By Add Date
            11. Meal Search
            12. Potato Meals
            13. Seafood Meals By Protein
            14. Sweet Meals
            0. Exit
            
            Enter your choice: 
        """.trimIndent())
    }

    private fun readMenuChoice(): Int {
        return readLine()?.toIntOrNull() ?: -1
    }
}