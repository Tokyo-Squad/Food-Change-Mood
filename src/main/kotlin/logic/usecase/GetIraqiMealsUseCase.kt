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
        return hasIraqiTag(meal) || isContainsIraqInDescription(meal)
    }

    private fun hasIraqiTag(meal: Meal): Boolean {
        return meal.tags.any { it.equals("iraqi", ignoreCase = true) }

    }

    private fun isContainsIraqInDescription(meal: Meal): Boolean {
        return meal.description?.equals("iraq", ignoreCase = true) ?: false
    }


    private fun getAllMeals(): List<Meal> = mealRepository.getMeals()

}
