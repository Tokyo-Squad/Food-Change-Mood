package org.example.logic

import org.example.model.Meal
import org.example.utils.MealSearchIndex

class MealSearchUseCase(
    private val csvRepository: CsvRepository
) {
    fun searchMeals(query: String): List<Meal> {
        return searchIndex.search(query)
    }

    private val searchIndex by lazy {
        MealSearchIndex(csvRepository.getMeals())
    }
}