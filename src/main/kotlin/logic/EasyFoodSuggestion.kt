package org.example.logic

import org.example.model.FormattedMeal
import org.example.model.Meal
import org.example.utiles.MealFormatter
import org.example.utiles.MealPrinter

class EasyFoodSuggestion(
    private val csvRepository: CsvRepository,
    private val formatter: MealFormatter = MealFormatter(),
    private val foodSuggestionPrinter: MealPrinter = MealPrinter()
) {

    fun showEasyMeals() {
        getEasyMeals().forEachIndexed { index, meal ->
            foodSuggestionPrinter.printMeal(meal, index)
        }
    }

    private fun getEasyMeals(): List<FormattedMeal> {
        return csvRepository.getMeals()
            .filter { isEasyMeal(it) }
            .shuffled()
            .take(10)
            .map { formatter.format(it) }
    }

    private fun isEasyMeal(meal: Meal): Boolean {
        return meal.preparationTime <= 30 && meal.numberOfIngredients <= 5 && meal.numberOfSteps <= 6
    }
}