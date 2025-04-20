package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class GetIraqiMealsUseCase(private val mealRepository: MealRepository) {

    fun invoke(): List<Meal> {
        return getAllMeals().filter {
            isIraqiMeal(it)
        }
    }

    private fun isIraqiMeal(meal: Meal): Boolean {
        val hasIraqiTag = meal.tags.any { it.equals("iraqi", ignoreCase = true) }
        val containsIraqInDescription = meal.description?.equals("iraq", ignoreCase = true) ?: false
        return hasIraqiTag || containsIraqInDescription
    }

    private fun getAllMeals(): List<Meal> = mealRepository.getMeals()

}
