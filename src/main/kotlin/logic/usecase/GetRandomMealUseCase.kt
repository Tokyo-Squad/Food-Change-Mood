package org.example.logic.usecase

import org.example.logic.CsvRepository
import org.example.model.Meal

class GetRandomMealUseCase(
    private val repo: CsvRepository
) {
    operator fun invoke(): Meal = repo.getMeals().random()
}