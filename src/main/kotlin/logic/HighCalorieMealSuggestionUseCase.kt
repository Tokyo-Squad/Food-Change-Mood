package org.example.logic

import org.example.model.Meal

class HighCalorieMealSuggestionUseCase(
    private val csvRepository: CsvRepository
) {
    private val highCalorieMeals: List<Meal> by lazy {
        csvRepository.getMeals().filter { it.nutrition.calories > 700 }
    }
    private val suggestedMealIds = mutableSetOf<Int>()

    fun getNextSuggestion(): Meal? {
        val unsuggestedMeals = highCalorieMeals.filter { !suggestedMealIds.contains(it.id) }
        if (unsuggestedMeals.isEmpty()) {
            return null
        }
        val suggestion = unsuggestedMeals.random()
        suggestedMealIds.add(suggestion.id)
        return suggestion
    }
}