package org.example.logic

import org.example.model.Meal
import org.example.utils.containAnyOf

class GetLargeGroupItalyMealUseCase(
    private val csvRepository: CsvRepository
) {

    fun getLargeGroupItalyMeal(): List<Meal>{
        return getItalyMeals().filter { it.tags.contains("for-large-groups") }
    }

    private fun getItalyMeals(): List<Meal> =  getAllMeals().filter { it.containAnyOf("italy") }

    private fun getAllMeals(): List<Meal> = csvRepository.getMeals()

}