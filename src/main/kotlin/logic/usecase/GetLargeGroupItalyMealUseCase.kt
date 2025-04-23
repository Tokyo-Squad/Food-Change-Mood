package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.containAnyOf

class GetLargeGroupItalyMealUseCase(
    private val mealRepository: MealRepository
) {
    private companion object {
        private val ITALIAN_KEYWORDS = listOf("italy", "italian", "pizza", "pasta", "risotto")
        private const val LARGE_GROUP_TAG = "for-large-groups"
    }

    operator fun invoke(): List<Meal> {
        return mealRepository.getMeals()
            .filter { meal ->
                isItalian(meal) && isSuitableForLargeGroups(meal)
            }
    }

     private fun isItalian(meal: Meal): Boolean =
        meal.containAnyOf(ITALIAN_KEYWORDS)


     private fun isSuitableForLargeGroups(meal: Meal): Boolean =
        meal.tags.any { it.equals(LARGE_GROUP_TAG, ignoreCase = true) }
}