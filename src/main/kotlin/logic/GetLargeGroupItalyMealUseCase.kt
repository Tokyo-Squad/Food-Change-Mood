package org.example.logic

import org.example.model.Meal
import org.example.utils.containAnyOf

class GetLargeGroupItalyMealUseCase(
    private val csvRepository: CsvRepository
) {

    operator fun invoke(): List<Meal> {
        return csvRepository.getMeals()
            .filter(::isItalianMeal)
            .filter(::isSuitableForLargeGroups)
    }

    private fun isItalianMeal(meal: Meal): Boolean =
        meal.containAnyOf("italy") || meal.containAnyOf("italian")

    private fun isSuitableForLargeGroups(meal: Meal): Boolean =
        meal.tags.contains("for-large-groups")

}