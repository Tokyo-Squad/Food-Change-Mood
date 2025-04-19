package org.example.logic

import org.example.model.Meal

class KetoDietMealHelperUseCase(
    private val csvRepository: CsvRepository,
    ): CalculatePercentage, ReactionProvider{

    private val disLikedFood = mutableSetOf<Meal>()
    private val likedFood  = mutableSetOf<Meal>()

    operator fun invoke(): Meal {
        val friendlyMeals = csvRepository.getMeals().filter { isKetoDietMeal(it) }
         return generateSequence { friendlyMeals.random() }.filterNot { disLikedFood.contains(it) }.first()
    }


    private fun isKetoDietMeal(meal: Meal): Boolean {
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

    override fun like(meal: Meal) {
        likedFood.add(meal)
    }

    override fun dislike(meal: Meal){
        disLikedFood.add(meal)
    }


}