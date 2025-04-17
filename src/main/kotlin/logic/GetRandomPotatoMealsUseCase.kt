package org.example.logic

import org.example.model.Meal
import org.example.utils.randomElementsUnique

class GetRandomPotatoMealsUseCase {
    operator fun invoke(meals: List<Meal>, randomSize: Int = 10): List<Meal> =
        meals.filter { meal ->
            meal.ingredients.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true) || ingredient.contains(
                    "potatoes",
                    ignoreCase = true
                )
            }
        }.toList().randomElementsUnique(randomSize)
}
