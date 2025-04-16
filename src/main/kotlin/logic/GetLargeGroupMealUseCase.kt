package org.example.logic

import org.example.model.Meal
import org.example.utils.MealNotFoundException
import org.example.utils.containAnyOf

class GetLargeGroupItalyMealUseCase(
    private val csvRepository: CsvRepository
) {

    fun getLargeGroupItalyMeal(): List<Meal> {
        return getItalyMeals().filter { it.tags.contains("for-large-groups") }
            .takeIf { it.isNotEmpty() }
            ?: throw MealNotFoundException("No meals found for large groups")
    }

    private fun getItalyMeals(): List<Meal> = getAllMeals()
        .filter {
            it.containAnyOf("italy") ||
                    it.containAnyOf("italian")
        }
        .takeIf { it.isNotEmpty() }
        ?: throw MealNotFoundException("No meals found for large groups")

    private fun getAllMeals(): List<Meal> = csvRepository.getMeals().takeIf { it.isNotEmpty() }
        ?: throw MealNotFoundException("No meals found for large groups")

}