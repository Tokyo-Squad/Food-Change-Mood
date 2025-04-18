package org.example.logic

import org.example.model.Meal
import org.example.utils.containAnyOf

class GetSweetMealsNotContainEggUseCase(
    private val sweetMeal: GetSweetMealUseCase
) {

    operator fun invoke(): List<Meal>{
        return sweetMeal().filter { meal ->
            !meal.containAnyOf("egg")
        }
    }
}