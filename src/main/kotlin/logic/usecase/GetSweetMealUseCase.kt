package org.example.logic.usecase

import org.example.logic.CsvRepository
import org.example.model.Meal
import org.example.utils.containAnyOf

class GetSweetMealUseCase(
    private val csvRepository: CsvRepository,
    ) {

    operator fun invoke(): List<Meal>{
        val sweetTypes = listOf("sweet", "dessert", "cake", "candy", "cookie", "pie", "chocolate", "sugar")
        return csvRepository.getMeals().filter { meal ->
            meal.containAnyOf(sweetTypes)
        }
    }
}