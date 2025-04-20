package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.randomElementsUnique

class GetRandomPotatoMealsUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(randomSize: Int = 10): List<Meal> =
        mealRepository.getMeals().filter { meal ->
            meal.ingredients.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true) || ingredient.contains("potatoes", ignoreCase = true)
            }
        }.randomElementsUnique(randomSize)
}
