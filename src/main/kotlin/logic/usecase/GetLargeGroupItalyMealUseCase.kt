package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.containAnyOf

class GetLargeGroupItalyMealUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke(): List<Meal> {
        return mealRepository.getMeals()
            .filter(::isItalianMeal)
            .filter(::isSuitableForLargeGroups)
    }

    private fun isItalianMeal(meal: Meal): Boolean =
        meal.containAnyOf("italy") || meal.containAnyOf("italian")

    private fun isSuitableForLargeGroups(meal: Meal): Boolean =
        meal.tags.contains("for-large-groups")

}