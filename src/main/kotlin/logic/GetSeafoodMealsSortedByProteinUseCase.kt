package org.example.logic

import org.example.model.Meal

/**
 * Use case for retrieving seafood meals sorted by their protein content in descending order.
 * It returns a list of pairs where each pair contains an index (starting at 1) and a Meal.
 */
class GetSeafoodMealsSortedByProteinUseCase {

    operator fun invoke(meals: List<Meal>): List<Pair<Int, Meal>> {
        val seafoodMeals = meals
            .filter(::isSeafoodMeal)
            .sortedByDescending { meal -> meal.nutrition.protein }

        // Map the sorted list to a pair of (index, meal), starting index count from 1
        return seafoodMeals.mapIndexed { index, meal ->
            Pair(index + 1, meal)
        }
    }

    private fun isSeafoodMeal(meal: Meal): Boolean =
        meal.description?.contains("seafood", ignoreCase = true) == true ||
                meal.tags.any { tag -> tag.contains("seafood", ignoreCase = true) }

}
