package org.example.logic

import org.example.model.Meal

class GetRandomMealUseCase(
    private val csvRepository: CsvRepository
) {
    operator fun invoke(): Meal{
        return csvRepository.getMeals().random()
    }
}