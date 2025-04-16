package org.example.data

import org.example.model.Meal

class  GymHelper {
    fun getMealsForGymTarget(
        meals: List<Meal>,
        targetCalories: Float,
        targetProtein: Float,
        calMargin: Float = 50f,
        proteinMargin: Float = 10f
    ): List<Meal> {
        return meals.filter {
            val calDiff = kotlin.math.abs(it.nutrition.calories - targetCalories)
            val proteinDiff = kotlin.math.abs(it.nutrition.protein - targetProtein)
            calDiff <= calMargin && proteinDiff <= proteinMargin
        }
    }
}