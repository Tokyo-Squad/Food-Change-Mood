package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal

/**
 * Use case for retrieving seafood meals sorted by their protein content in descending order.
 * It returns a list of pairs where each pair contains an index (starting at 1) and a Meal.
 */
class GetSeafoodMealsSortedByProteinUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke(): List<Pair<Int, Meal>> {
        val seafoodMeals = mealRepository.getMeals()
            .filter(::isSeafoodMeal)
            .sortedByDescending { meal -> meal.nutrition.protein }

        return seafoodMeals.mapIndexed { index, meal ->
            Pair(index + 1, meal)
        }
    }

    private fun isSeafoodMeal(meal: Meal): Boolean =
        meal.description?.contains("seafood", ignoreCase = true) == true ||
                meal.tags.any { tag -> tag.contains("seafood", ignoreCase = true) }

}
