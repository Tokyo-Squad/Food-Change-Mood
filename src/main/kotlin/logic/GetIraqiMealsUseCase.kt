package org.example.logic

import org.example.model.Meal

class GetIraqiMealsUseCase(private val csvRepository: CsvRepository) {

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

    private fun getAllMeals(): List<Meal> = csvRepository.getMeals()

}
