package org.example.logic.usecase

import org.example.logic.CsvRepository
import org.example.model.Meal
import org.example.utils.MealSearchIndex

class GetMealsByNameUseCase(
    private val csvRepository: CsvRepository
) {
    operator fun invoke(query: String): List<Meal> {
        return searchIndex.search(query)
    }

    private val searchIndex by lazy {
        MealSearchIndex(csvRepository.getMeals())
    }
}