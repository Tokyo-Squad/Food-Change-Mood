package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class GetRandomMealUseCase(
    private val repo: MealRepository
) {
    operator fun invoke(): Meal = repo.getMeals().random()
}