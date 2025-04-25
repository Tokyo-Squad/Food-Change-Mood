package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.MealAppException
import org.example.utils.MealSearchIndex

class GetMealsByNameUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(query: String): List<Meal> {
        return try {
            searchIndex.search(query)
        } catch (e: MealAppException.InvalidArgumentException) {
            emptyList()
        }
    }

    private val searchIndex by lazy {
        MealSearchIndex(mealRepository.getMeals())
    }
}