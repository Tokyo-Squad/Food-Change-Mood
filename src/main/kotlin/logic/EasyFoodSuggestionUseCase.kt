package org.example.logic

import org.example.model.Meal

class EasyFoodSuggestionUseCase(
    private val csvRepository: CsvRepository,
) {

    fun getEasyMeals(): List<Meal> {
        return csvRepository.getMeals()
            .filter { isEasyMeal(it) }
            .shuffled()
            .take(10)
    }

    private fun isEasyMeal(meal: Meal): Boolean {
        return meal.preparationTime <= 30 && meal.numberOfIngredients <= 5 && meal.numberOfSteps <= 6
    }
}