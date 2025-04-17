package org.example.logic

import org.example.model.Meal

class KetoDietMealHelperUseCase(
    private val csvRepository: CsvRepository,
    ): CalculatePercentage, DisLikeProvider{

    private val disLikedFood = mutableSetOf<Meal>()

    fun getRandomFriendlyMeal(): Meal {
        val friendlyMeals = getAllMeal().filter { isFriendlyMeal(it) }
         return generateSequence { friendlyMeals.random() }.filterNot { disLikedFood.contains(it) }.first()
    }

    private fun getAllMeal(): List<Meal> = csvRepository.getMeals()

    private fun isFriendlyMeal(meal: Meal): Boolean {
        val nutrition = meal.nutrition

        val totalCalories = nutrition.calories
        val totalFat = nutrition.totalFat
        val protein = nutrition.protein
        val carbs = nutrition.carbohydrates

        val fatCalories = totalFat * 9f
        val proteinCalories = protein * 4f
        val carbCalories = carbs * 4f

        val fatPercentage = calculatePercentage(fatCalories, totalCalories)
        val proteinPercentage = calculatePercentage(proteinCalories, totalCalories)
        val carbPercentage = calculatePercentage(carbCalories, totalCalories)

        return fatPercentage in 70f..75f &&
                proteinPercentage in 15f..20f &&
                carbPercentage in 5f..10f

    }

    override fun calculatePercentage(firstValue: Double, secondValue: Double): Double {
        return (firstValue / secondValue) * 100
    }

    override fun dislike(meal: Meal){
        disLikedFood.add(meal)
    }
}