package org.example.logic.usecase

import org.example.logic.CsvRepository
import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val repository: CsvRepository
) {
    operator fun invoke(): List<Meal> {
        val allMeals = repository.getMeals()

        // Compute average values across the dataset
        val avgTotalFat = allMeals.map { it.nutrition.totalFat }.average()
        val avgSaturatedFat = allMeals.map { it.nutrition.saturatedFat }.average()
        val avgCarbs = allMeals.map { it.nutrition.carbohydrates }.average()

        return filterHealthyFastMeals(allMeals, avgTotalFat, avgSaturatedFat, avgCarbs)

    }

    private fun filterHealthyFastMeals(
        allMeals: List<Meal>, avgTotalFat: Double, avgSaturatedFat: Double, avgCarbs: Double
    ): List<Meal> {
        return allMeals.asSequence().filter { meal ->
                meal.preparationTime <= 15 && meal.nutrition.totalFat <= avgTotalFat && meal.nutrition.saturatedFat <= avgSaturatedFat && meal.nutrition.carbohydrates <= avgCarbs
            }.sortedBy { it.nutrition.totalFat }.toList()
    }
}