package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.containAnyOf

class GetSweetMealUseCase(
    private val mealRepository: MealRepository,
    ) {

    operator fun invoke(): List<Meal>{
        val sweetTypes = listOf("sweet", "dessert", "cake", "candy", "cookie", "pie", "chocolate", "sugar")
        return mealRepository.getMeals().filter { meal ->
            meal.containAnyOf(sweetTypes)
        }
    }
}