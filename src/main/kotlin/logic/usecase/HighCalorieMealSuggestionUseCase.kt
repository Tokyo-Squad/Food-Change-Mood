package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class HighCalorieMealSuggestionUseCase(
    private val mealRepository: MealRepository
) {
    private val highCalorieMeals: List<Meal> by lazy {
        mealRepository.getMeals().filter { it.nutrition.calories > 700 }
    }
    private val suggestedMealIds = mutableSetOf<Int>()

    fun getNextSuggestion(): Meal? {
        val unSuggestedMeals = highCalorieMeals.filter { !suggestedMealIds.contains(it.id) }
        val suggestion = if (unSuggestedMeals.isEmpty()) null else unSuggestedMeals.random()
        suggestion?.let { suggestedMealIds.add(it.id) }
        return suggestion
    }
}