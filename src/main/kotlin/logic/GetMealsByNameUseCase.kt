package org.example.logic

import org.example.model.Meal
import org.example.utils.MealSearchIndex

class GetMealsByNameUseCase(
    private val csvRepository: CsvRepository
) {
    operator fun invoke(query: String): List<Meal> {
        println("--- please wait ---")
        return searchIndex.search(query)
    }

    private val searchIndex by lazy {
        MealSearchIndex(csvRepository.getMeals())
    }
}