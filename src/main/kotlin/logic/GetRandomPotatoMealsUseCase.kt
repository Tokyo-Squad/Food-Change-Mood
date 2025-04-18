package org.example.logic

import org.example.model.Meal
import org.example.utils.randomElementsUnique

class GetRandomPotatoMealsUseCase(
    private val csvRepository: CsvRepository
) {
    operator fun invoke(randomSize: Int = 10): List<Meal> =
        csvRepository.getMeals().filter { meal ->
            meal.ingredients.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true) || ingredient.contains("potatoes", ignoreCase = true)
            }
        }.randomElementsUnique(randomSize)
}
