package org.example.logic

import org.example.model.Meal

class GetRandomMealUseCase(
    private val repo: CsvRepository
) {
    operator fun invoke(): Meal = repo.getMeals().random()
}