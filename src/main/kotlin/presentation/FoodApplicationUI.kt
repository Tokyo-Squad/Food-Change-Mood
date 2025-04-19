package org.example.presentation

import org.example.presentation.controller.*


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
                1 -> executeWithPause { healthyFastFoodMealsController.display() }
                2 -> executeWithPause { mealSearchController.display() }
                3 -> executeWithPause { iraqiMealsController.display() }
                4 -> executeWithPause { easyFoodSuggestionController.display() }
                5 -> executeWithPause { ingredientGameController.display() }
                6 -> executeWithPause { sweetMealsController.displayEggFreeSweets() }
                7 -> executeWithPause { ketoDietController.display() }
                8 -> executeWithPause { mealsByAddDateController.display() }
                9 -> executeWithPause { gymHelperController.display() }
                10 -> executeWithPause { exploreFoodCultureController.display() }
                11 -> executeWithPause { ingredientGameController.display() }
                12 -> executeWithPause { potatoMealsController.display() }
                13 -> executeWithPause { highCalorieMealSuggestionController.display() }
                14 -> executeWithPause { seafoodMealsByProteinController.display() }
                15 -> executeWithPause { italyLargeGroupMealsController.display() }
                0 -> break
                else -> println("Invalid choice. Please try again.")
            }
        }
    }

    private fun executeWithPause(action: () -> Unit) {
        action()
        println("\nPress Enter to continue...")
        readLine()
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