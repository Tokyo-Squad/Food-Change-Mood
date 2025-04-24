package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.randomElementsUnique

class EasyFoodSuggestionUseCase(
    private val mealRepository: MealRepository,
) {

    fun getEasyMeals(): List<Meal> {
        return mealRepository.getMeals()
            .filter { isEasyMeal(it) }
            .randomElementsUnique(10)
    }

    private fun isEasyMeal(meal: Meal): Boolean {
        return meal.preparationTime <= 30 && meal.numberOfIngredients <= 5 && meal.numberOfSteps <= 6
    }
}