package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.MealSearchIndex

class GetMealsByNameUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(query: String): List<Meal> {
        return searchIndex.search(query)
    }

    private val searchIndex by lazy {
        MealSearchIndex(mealRepository.getMeals())
    }
}