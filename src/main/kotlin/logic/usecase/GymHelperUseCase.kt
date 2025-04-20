package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class GymHelperUseCase(
    private val mealRepository: MealRepository
) {
    fun invoke(
        targetCalories: Float,
        targetProtein: Float,
        caloriesMargin: Float = 50f,
        proteinMargin: Float = 10f
    ): List<Meal> {
        return getAllMeals().filter {
            val calDiff = kotlin.math.abs(it.nutrition.calories - targetCalories)
            val proteinDiff = kotlin.math.abs(it.nutrition.protein - targetProtein)
            calDiff <= caloriesMargin && proteinDiff <= proteinMargin
        }
    }

    private fun getAllMeals(): List<Meal> = mealRepository.getMeals()

}