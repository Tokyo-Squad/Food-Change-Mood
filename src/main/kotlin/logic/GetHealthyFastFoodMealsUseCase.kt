package org.example.logic

import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val repository: CsvRepository
) {
    fun getHealthyFastFoodMeals(): List<Meal> {
        val allMeals = repository.getMeals()

        // Compute average values across the dataset
        val avgTotalFat = allMeals.map { it.nutrition.totalFat }.average()
        val avgSaturatedFat = allMeals.map { it.nutrition.saturatedFat }.average()
        val avgCarbs = allMeals.map { it.nutrition.carbohydrates }.average()

        return allMeals
            .asSequence()
            .filter { meal ->
                meal.preparationTime <= 15 &&
                        meal.tags.any { it.equals("fast food", ignoreCase = true) } &&
                        meal.nutrition.totalFat < avgTotalFat &&
                        meal.nutrition.saturatedFat < avgSaturatedFat &&
                        meal.nutrition.carbohydrates < avgCarbs
            }
            .sortedBy { it.nutrition.totalFat }
            .toList()
    }
}